import { onMessage } from 'firebase/messaging';
import messaging from './firebase';
// import navigateInstance from './navigate';

export const eventMap = {};

export const registMessageEvent = (event, type) => {
  eventMap[type] = event;

  onMessage(messaging, (payload) => {
    console.log(payload);
    const message = JSON.parse(payload.data.json);
    const handler = eventMap[message.type];
    if (handler) handler(message);
  });
};
