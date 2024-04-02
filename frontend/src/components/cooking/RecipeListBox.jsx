import { Link } from 'react-router-dom';

export default function RecipeListBox({ recipeList, selected }) {
  return (
    <div className="history-recipe-list">
      {recipeList.map(({ recipeId, imgUrl, name, cookTime }) => (
        <Link
          to={`/Cooking/recipe/${recipeId}`}
          state={{ selected }}
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
                {`${cookTime}min`}
              </div>
              <div className="recipe-item-description-name">{name}</div>
            </div>
          </div>
        </Link>
      ))}
    </div>
  );
}
