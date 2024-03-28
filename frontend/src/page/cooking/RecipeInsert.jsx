import { Link, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';

import { ingredientData } from '../../utils/data';

import '../../assets/styles/cooking/recipeinsert.css';

export default function RecipeInsert() {
  const navigate = useNavigate();
  const recipeDetail = JSON.parse(sessionStorage.getItem('recipeInfo'));
  const containIngredeintList = recipeDetail.ingredientList.map(
    ({ ingredientId }) => ingredientId,
  );
  const containSeasoningList = recipeDetail.seasoningList.map(
    ({ ingredientId }) => ingredientId,
  );

  const containComponentList = [
    ...containIngredeintList,
    ...containSeasoningList,
  ];

  const [insertedList, setInsertedList] = useState(
    sessionStorage.getItem('addIngredientList')
      ? JSON.parse(sessionStorage.getItem('addIngredientList'))
      : [],
  );
  const [ingredientList, setIngredientList] = useState(
    ingredientData
      .toSorted((a, b) => (a.name > b.name ? 1 : -1))
      .filter(
        ({ ingredientId }) => !containComponentList.includes(ingredientId),
      ),
  );

  const insertItem = (item) => {
    setInsertedList([...insertedList, item]);
    setIngredientList(
      ingredientList.filter(
        (ingredient) => item.ingredientId !== ingredient.ingredientId,
      ),
    );
  };

  const deleteItem = (item) => {
    setIngredientList([...ingredientList, item]);
    setInsertedList(
      insertedList.filter(
        (ingredient) => item.ingredientId !== ingredient.ingredientId,
      ),
    );
  };

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
          레시피 재료를 추가해주세요
        </div>
        <div className="recipe-insert-search-wrapper">
          <input
            className="recipe-insert-search-input"
            placeholder="재료를 검색해보세요"
          />
          <img
            className="recipe-insert-search-icon"
            src="/images/cooking/search.svg"
            alt="재료 검색"
          />
        </div>
        <div className="recipe-insert-search-result">
          {ingredientList.map(({ ingredientId, name, image }) => (
            <div
              className="recipe-insert-item"
              key={ingredientId}
              onClick={() => {
                insertItem({ ingredientId, name, image });
              }}
            >
              <img
                className="recipe-insert-item-image"
                src={image}
                alt={name}
              />
              <div className="recipe-insert-item-name">{name}</div>
              <img
                className="recipe-insert-item-button"
                src="/images/cooking/plus_orange.svg"
                alt="재료 추가"
              />
            </div>
          ))}
        </div>
        <div className="recipe-insert-box-separator" />
        <div className="recipe-insert-components-list">
          {insertedList.map(({ ingredientId, name, image }) => (
            <div
              className="recipe-insert-components-item"
              key={ingredientId}
              onClick={() => {
                deleteItem({ ingredientId, name, image });
              }}
            >
              <img
                className="recipe-insert-components-item-image"
                src={image}
                alt="name"
              />
              <div className="recipe-insert-components-item-name f-1">
                {name}
              </div>
              <img
                className="recipe-insert-components-item-delete"
                src="/images/cooking/delete.svg"
                alt="재료 삭제"
              />
            </div>
          ))}
        </div>
      </div>
      <Link
        to="/Cooking/recipe/1/save"
        className="recipe-insert-complete-button box-shadow link f-3"
        onClick={() => {
          sessionStorage.setItem(
            'addIngredientList',
            JSON.stringify(insertedList),
          );
        }}
      >
        완료
      </Link>
      <style>{'.footer-container { display : none }'}</style>
    </div>
  );
}
