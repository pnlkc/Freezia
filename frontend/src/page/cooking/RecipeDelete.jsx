import { Link, useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';

import '../../assets/styles/cooking/recipeinsert.css';
import { ingredientImageErrorHander } from '../../utils/imageErrorHandler';

export default function RecipeInsert() {
  const recipeDetail = JSON.parse(sessionStorage.getItem('recipeInfo'));
  const [removeIngredientList, setRemoveIngredientList] = useState(
    sessionStorage.getItem('removeIngredientList')
      ? JSON.parse(sessionStorage.getItem('removeIngredientList'))
      : [],
  );
  const containIngredeintList = recipeDetail.ingredientList;
  const containSeasoningList = recipeDetail.seasoningList;

  const containComponentList = [
    ...containIngredeintList,
    ...containSeasoningList,
  ];
  const [ingredientList, setIngredientList] = useState(
    containComponentList.toSorted((a, b) => (a.name > b.name ? 1 : -1)),
  );

  const navigate = useNavigate();

  return (
    <div className="recipe-insert-container">
      <div
        className="recipe-insert-back-button f-5"
        onClick={() => {
          navigate(-1);
        }}
      >
        {'<'}
      </div>
      <div className="recipe-insert-box">
        <div className="recipe-insert-title f-4">
          사용하지 않은 재료를 선택해 주세요
        </div>
        <form className="recipe-insert-search-result">
          {ingredientList.map(({ ingredientId, name, image }) => (
            <label
              className="recipe-insert-item"
              key={ingredientId}
              htmlFor={ingredientId}
            >
              <img
                className="recipe-insert-item-image"
                src={image}
                alt={name}
                onError={ingredientImageErrorHander}
              />
              <div className="recipe-insert-item-name">{name}</div>
              <input
                id={ingredientId}
                type="checkbox"
                className="recipe-delete-components-item-delete"
                checked={
                  !removeIngredientList
                    .map((removeIngredient) => removeIngredient.ingredientId)
                    .includes(ingredientId)
                }
                onChange={(e) => {
                  if (!e.target.checked) {
                    setRemoveIngredientList([
                      ...removeIngredientList,
                      { ingredientId, name, image },
                    ]);
                  } else {
                    setRemoveIngredientList(
                      removeIngredientList.filter(
                        (removeIngredient) =>
                          removeIngredient.ingredientId !== ingredientId,
                      ),
                    );
                  }
                }}
              />
            </label>
          ))}
        </form>
      </div>
      <Link
        to="/Cooking/recipe/1/save"
        className="recipe-insert-complete-button box-shadow link f-3"
        onClick={() => {
          sessionStorage.setItem(
            'removeIngredientList',
            JSON.stringify(removeIngredientList),
          );
        }}
      >
        완료
      </Link>
      <style>{'.footer-container { display : none }'}</style>
    </div>
  );
}
