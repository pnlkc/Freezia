import { EventSourcePolyfill, NativeEventSource } from 'event-source-polyfill';
import parser from '../utils/replyParser';

const EventSource = EventSourcePolyfill || NativeEventSource;
let setRecipe;
let setIsProgress;
let eventSource;

export const registHook = (newSetRecipe, newSetIsProgress) => {
  setRecipe = newSetRecipe;
  setIsProgress = newSetIsProgress;

  if (eventSource) {
    eventSource.onmessage = (event) => {
      try {
        if (event.data.trim() === '') return;
        parser.parse(event.data);
        if (setRecipe) setRecipe(JSON.parse(JSON.stringify(parser.recipe)));
      } catch (error) {
        console.error(error);
      }
    };

    eventSource.onerror = (event) => {
      console.log('응답 종료');
      eventSource.close();
      sessionStorage.setItem('isProgress', false);
      sessionStorage.setItem('isDone', true);
      if (setIsProgress) setIsProgress(false);
    };
  }
};

const sendMessage = async (url, newSetRecipe, newSetIsProgress) => {
  eventSource = new EventSource(`${import.meta.env.VITE_BASE_URL}/${url}`, {
    headers: {
      Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`,
    },
  });

  parser.reset();
  registHook(newSetRecipe, newSetIsProgress);
  sessionStorage.setItem('isDone', false);
  sessionStorage.setItem('isProgress', true);
};

export default sendMessage;
