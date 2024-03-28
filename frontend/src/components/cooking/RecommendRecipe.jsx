import '../../assets/styles/cooking/recommendrecipe.css';
import RecommendRecipeCarousel from './RecommendRecipeCarousel';

export default function RecommendRecipe() {
  const userInfo = JSON.parse(sessionStorage.getItem('profile'));

  return (
    <div className="recommend-recipe-container">
      <div className="recommend-recipe-title f-2">
        <span className="bold">{userInfo.name}</span>
        <span className="o-7">님을 위한</span>
        <span className="bold"> 레시피</span>
      </div>
      <div className="recommend-recipe-contents-box box-shadow">
        <RecommendRecipeCarousel />
      </div>
    </div>
  );
}
