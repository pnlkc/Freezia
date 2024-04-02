import { Link } from 'react-router-dom';

import '../../assets/styles/cooking/recipefinishcard.css';
import { disconnectWatch } from '../../apis/firebase';

export default function RecipeFinsishCard() {
  const recipeDetail = JSON.parse(sessionStorage.getItem('recipeInfo'));

  return (
    <div className="recipe-finish-card box-shadow">
      <div className="recipe-finish-card-image-box">
        <img
          className="recipe-finish-card-image"
          src={recipeDetail.imgUrl}
          alt="음식 사진"
        />
        <div className="recipe-finish-card-name f-4">{recipeDetail?.name}</div>
        <div className="recipe-finish-card-bookmark-wrapper"></div>
      </div>
      <div className="recipe-finish-card-box">
        <div className="recipe-finsih-card-info-box">
          <div className="recipe-finish-card-info-wrapper">
            <div className="f-1">요리 시간</div>
            <div className="f-2">{`${recipeDetail?.cookTime} m`}</div>
          </div>
          <div className="recipe-finish-card-info-wrapper">
            <div className="f-1">칼로리</div>
            <div className="f-2">{`${recipeDetail.calorie} kcal`}</div>
          </div>
        </div>
        <div className="recipe-finish-card-button-box">
          <Link
            to="/Cooking/recipe/1/save"
            className="finish-card-button-save box-shadow link"
          >
            요리 해먹었어요
          </Link>
          <Link
            to="/Cooking/recipe/list"
            className="finish-card-button-exit box-shadow link"
            onClick={() => {
              disconnectWatch(recipeDetail.recipeId);
            }}
          >
            레시피 목록으로 이동
          </Link>
        </div>
      </div>
    </div>
  );
}
