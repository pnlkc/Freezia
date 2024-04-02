import axios from './axios';

export const getRecipeDetail = async (recipeId) => {
  const { data } = await axios.get(`recipes/${recipeId}`);
  sessionStorage.setItem('recipeInfo', JSON.stringify(data.recipeInfo));

  return data.recipeInfo;
};

export const getRecipeSteps = async (recipeId) => {
  const { data } = await axios.get(`recipes/${recipeId}/steps`);
  sessionStorage.setItem('recipeSteps', JSON.stringify(data.recipeSteps));

  return data.recipeSteps;
};
