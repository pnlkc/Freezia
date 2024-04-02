import { useNavigate } from 'react-router-dom';

import '../../assets/styles/cooking/createrecipecard.css';
import { useEffect, useState } from 'react';

export default function CreateRecipeCard({ name, imgUrl }) {
  const [recipeId, setReceipId] = useState(0);
  const navigate = useNavigate();

  useEffect(() => {
    setReceipId(sessionStorage.createRecipeId);
  }, [imgUrl]);

  return (
    <div>
      {imgUrl ? (
        <div className="create-recipe-card-container">
          <img
            className="create-recipe-card-image box-shadow"
            src={imgUrl}
            alt="이미지"
          />
          <div
            className="create-recipe-background-filter"
            onClick={() => {
              navigate(`/Cooking/recipe/${recipeId}`);
            }}
          />
          <div className="create-recipe-card-name">{name}</div>
        </div>
      ) : (
        <div></div>
      )}
    </div>
  );
}
