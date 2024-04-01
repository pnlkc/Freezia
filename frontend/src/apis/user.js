// import axios from 'axios';
import axios from './axios';

export const getMemberList = async () => {
  const { data } = await axios.get('/members');
  return data.memberList;
};

export const getFridgeIngredients = async () => {
  const { data } = await axios.get('/fridge-ingredients');

  const ingredients = [...data.fridgeIngredients];
  sessionStorage.setItem('ingredients', JSON.stringify(ingredients));
  return ingredients;
};

export const getUserInfo = async () => {
  const { data } = await axios.get('/members/info');
  sessionStorage.setItem('profile', JSON.stringify(data.member));

  return data;
};

export const getRecipeList = async () => {
  const { data } = await axios.get('/recipes');
  sessionStorage.setItem('recipeList', JSON.stringify(data.recipes));

  return data.recipes;
};

export const getShoppingList = async () => {
  const { data } = await axios.get('/shopping-list');
  sessionStorage.setItem('shoppingList', JSON.stringify(data.shoppingList));

  return data.shoppingList;
};

export const getRecommendationRecipeList = async () => {
  const { data } = await axios.get('/recipes/recommendation');
  sessionStorage.setItem('recommendation', JSON.stringify(data.recipes));

  return data.recipes;
};

export const getSaveRecipeList = async () => {
  const { data } = await axios.get('/recipes/history/save');
  sessionStorage.setItem('save', JSON.stringify(data.recipes));

  return data.recipes;
};

export const getCompleteRecipeList = async () => {
  const { data } = await axios.get('/recipes/history/complete');
  sessionStorage.setItem('complete', JSON.stringify(data.recipes));

  return data.recipes;
};

export const selectUser = async (memberId) => {
  const { data } = await axios.post('/members', {
    memberId,
  });

  sessionStorage.setItem('accessToken', data.accessToken);

  await getUserInfo();
  await getRecipeList();
  await getFridgeIngredients();
  await getShoppingList();
  await getSaveRecipeList();
  await getCompleteRecipeList();
  await getRecommendationRecipeList();

  return data;
};

export const deleteIngredient = (fridgeIngredientId) => {
  axios.delete('fridge-ingredients', {
    data: {
      fridgeIngredientId,
    },
  });
};
