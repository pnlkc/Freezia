import { useEffect, useState } from 'react';
import { ingredientImageErrorHander } from '../utils/imageErrorHandler';

import '../assets/styles/ingredients.css';
import { deleteIngredient, getFridgeIngredients } from '../apis/user';

export default function Ingredients({ setOnIngredientManager, handleMode }) {
  const [ingredientList, setIngredientList] = useState(
    sessionStorage.ingredients ? JSON.parse(sessionStorage.ingredients) : [],
  );

  const [selected, setSelected] = useState([]);
  const changeHandler = (event, recipeId) => {
    if (!event.target.checked) {
      setSelected(selected.filter((id) => id === recipeId));
    } else {
      setSelected([...selected, recipeId]);
    }
  };

  useEffect(() => {
    getFridgeIngredients().then((ingredients) => {
      setIngredientList(ingredients);
    });
  }, []);

  return (
    <div className="ingredient-manage-container">
      <div className="ingredient-manage-box box-shadow">
        <div className="ingredient-manage-header">
          <div className="f-3 bold">식재료 선택하기</div>
          <img
            src="/images/cooking/delete.svg"
            alt="닫기"
            onClick={() => {
              setOnIngredientManager(false);
            }}
          />
        </div>
        <div className="ingredient-manage-list">
          {ingredientList.map(({ fridgeIngredientId, imgUrl, name }) => (
            <label
              className="ingredient-mange-item"
              key={fridgeIngredientId}
              htmlFor={fridgeIngredientId}
            >
              <img
                className="ingredient-manage-item-image"
                src={imgUrl}
                alt={name}
                onError={ingredientImageErrorHander}
              />
              <div className="ingredient-manage-item-name">{name}</div>
              <input
                type="checkbox"
                id={fridgeIngredientId}
                className="ingredient-manage-item-checkbox"
                onChange={(event) => {
                  changeHandler(event, fridgeIngredientId);
                }}
              />
            </label>
          ))}
        </div>
        <div
          className="ingrdient-manage-button"
          onClick={() => {
            handleMode('home');
            setOnIngredientManager(false);
            setTimeout(() => {
              selected.forEach((id) => {
                deleteIngredient(id);
              });
            }, 700);
          }}
        >
          재료 꺼내기
        </div>
      </div>
    </div>
  );
}
