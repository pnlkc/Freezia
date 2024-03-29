import axios from './axios';

export const setFcmToken = async (fridgeId, token) => {
  axios.post(`fridge/${fridgeId}`, {
    fridgeToken: token,
  });
};

export const connectWatch = async (recipeId) => {
  axios.post(`recipes/${recipeId}/galaxy-watch`);
};

export const disconnectWatch = async (recipeId) => {
  axios.get(`recipes/${recipeId}/galaxy-watch`);
};

export const moveStep = async (step) => {
  axios.get(`recipes/steps/${step}`);
};

export const setWatchToken = async (token) => {
  axios.post('fcm/token', {
    type: 2,
    token,
  });
};
