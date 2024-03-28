import axios from './axios';

export const saveCompleteRecipe = ({
  recipeId,
  addIngredients,
  removeIngredients,
  memo,
}) => {
  axios.post(`recipes/${recipeId}/complete`, {
    addIngredients,
    removeIngredients,
    memo,
  });
};

export const deleteCompleteRecipe = (completeCookId) => {
  axios.delete(`recipes/complete/${completeCookId}`);
};

export const getRecipeHistory = async (recipeId) => {
  const { data } = await axios.get(`recipes/${recipeId}/complete`);

  sessionStorage.setItem('completeCooks', JSON.stringify(data.completeCooks));

  return data.completeCooks;
};
