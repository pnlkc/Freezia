import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';

import FilterList from '../../components/cooking/FilterList';
import Header from '../../components/cooking/Header';
import { recipeTypeList } from '../../utils/data';

import '../../assets/styles/cooking/recipelist.css';
import { getRecipeList } from '../../apis/user';

export default function RecipeList() {
  const [recipeList, setRecipeList] = useState(
    JSON.parse(sessionStorage.recipeList),
  );
  const [selectedList, setSelectedList] = useState([]);
  const [filteredList, setFilteredList] = useState([]);

  useEffect(() => {
    getRecipeList().then((recipes) => {
      setRecipeList(recipes);
    });
  }, []);

  useEffect(() => {
    const filterSet = new Set(selectedList);
    if (selectedList.length === 0) {
      setFilteredList([...recipeList]);
    } else {
      setFilteredList(
        recipeList.filter((recipe) => {
          const typeSet = new Set(recipe.recipeTypes.split(','));
          return typeSet.intersection(filterSet).size !== 0;
        }),
      );
    }
  }, [selectedList]);

  return (
    <div className="cooking-recipe-list-container">
      <Header isHome />
      <FilterList
        recipeTypeList={recipeTypeList}
        selectedList={selectedList}
        setSelectedList={setSelectedList}
      />
      <div className="cooking-recipe-list">
        <Link
          to="/Cooking/recipe/create"
          className="recipe-create-button box-shadow link "
        >
          <div className="recipe-create-button-text bold f-2">
            레시피 생성하기
          </div>
        </Link>
        {filteredList.map(({ name, imgUrl, recipeId, cookTime }) => (
          <Link
            to={`/Cooking/recipe/${recipeId}`}
            key={recipeId}
            style={{ backgroundImage: `url(${imgUrl})` }}
            className="recipe-item-container box-shadow link"
          >
            <div className="recipe-item-description">
              <div className="recipe-item-background-filter" />
              <div className="recipe-item-description-wrapper">
                <div className="recipe-item-description-duration f-0 o-5">
                  <img
                    src="/images/cooking/time.svg"
                    alt="아이콘"
                    className="recipe-item-description-duration-icon"
                  />
                  {`${cookTime} min`}
                </div>
                <div className="recipe-item-description-name">{name}</div>
              </div>
            </div>
          </Link>
        ))}
      </div>
    </div>
  );
}
