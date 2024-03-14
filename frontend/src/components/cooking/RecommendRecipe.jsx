import '../../assets/styles/cooking/recommendrecipe.css';
import RecommendRecipeCarousel from './RecommendRecipeCarousel';

export default function RecommendRecipe() {
  return (
    <div className="recommend-recipe-container">
      <div className="recommend-recipe-title f-2">
        <span className="bold">당신</span>
        <span className="o-7">을 위한</span>
        <span className="bold"> 레시피</span>
      </div>
      <div className="recommend-recipe-contents-box">
        <RecommendRecipeCarousel />
      </div>
    </div>
  );
}
