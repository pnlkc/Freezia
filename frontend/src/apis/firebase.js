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
  axios.get(`recipes/${recipeId}/galaxy-watch`).then(() => {
    sessionStorage.setItem('isConnected', 'false');
  });
};

export const moveStep = async (step) => {
  axios.post(`recipes/steps/${step}`, { sender: 0 });
};

export const setWatchToken = async (token) => {
  axios.post('fcm/token', {
    type: 2,
    token,
  });
};

export const sendWarning = async () => {
  // const { data } = await axios.get('fridge-ingredients/fcmtest/1');
  await axios.post('fridge-ingredients', { name: '복숭아' });
  const { data } = await axios.get('fridge-ingredients');
  await axios.delete('fridge-ingredients', {
    data: {
      fridgeIngredientId:
        data.fridgeIngredients[data.fridgeIngredients.length - 1]
          .fridgeIngredientId,
    },
  });

  return data;
};
