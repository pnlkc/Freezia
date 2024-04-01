import { useState } from 'react';
import { ingredientImageErrorHander } from '../utils/imageErrorHandler';

import '../assets/styles/ingredients.css';

export default function Ingredients({ setOnIngredientManager }) {
  const [ingredientList, setIngredientList] = useState(
    sessionStorage.ingredients ? JSON.parse(sessionStorage.ingredients) : [],
  );

  return (
    <div className="ingredient-manage-container">
      <div className="ingredient-manage-box box-shadow">
        <div className="ingredient-manage-header">
          <div className="f-3 bold">식재료 관리</div>
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
            <div
              className="ingredient-mange-item"
              key={fridgeIngredientId}
              onClick={() => {
                sendWarning();
              }}
            >
              <img
                className="ingredient-manage-item-image"
                src={imgUrl}
                alt={name}
                onError={ingredientImageErrorHander}
              />
              <div className="ingredient-manage-item-name">{name}</div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
