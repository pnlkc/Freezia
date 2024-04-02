import { EventSourcePolyfill, NativeEventSource } from 'event-source-polyfill';
import parser from '../utils/replyParser';
import axios from './axios';

const EventSource = EventSourcePolyfill || NativeEventSource;
let setRecipe;
let setIsProgress;
let eventSource;

export const getCreateRecipeId = async () => {
  const { data } = await axios.get('generate-AI/new-recipe-id');

  sessionStorage.setItem('createRecipeId', data.recipeId);

  return data.recipeId;
};

export const updateImage = async ({ recipeId, recipeName }) => {
  const { data } = await axios.post(
    'generate-AI/generate-recipe-image-with-name',
    { recipeId, recipeName },
  );

  return data.imgUrl;
};

export const registHook = (newSetRecipe, newSetIsProgress) => {
  setRecipe = newSetRecipe;
  setIsProgress = newSetIsProgress;

  if (eventSource) {
    eventSource.onmessage = (event) => {
      try {
        parser.parse(event.data.replaceAll('#', ' '));
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
  const createRecipeId = await getCreateRecipeId();

  const newUrl = `${url}&recipeId=${createRecipeId}`;

  eventSource = new EventSource(`${import.meta.env.VITE_BASE_URL}/${newUrl}`, {
    headers: {
      Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`,
      'Content-Type': 'text/event-stream',
    },
  });

  parser.reset();
  registHook(newSetRecipe, newSetIsProgress);
  sessionStorage.setItem('isDone', false);
  sessionStorage.setItem('isProgress', true);
};

export default sendMessage;
