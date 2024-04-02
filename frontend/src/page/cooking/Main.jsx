import { Link } from 'react-router-dom';

import Header from '../../components/cooking/Header';
import UserProfile from '../../components/cooking/UserProfile';
import RecommendRecipe from '../../components/cooking/RecommendRecipe';

import '../../assets/styles/cooking/main.css';

export default function Main() {
  return (
    <div className="cooking-main-container">
      <Header isHome />
      <div className="cooking-main-content-box">
        <UserProfile />
        <RecommendRecipe />
        <div className="cooking-main-button-box">
          <Link
            to="/Cooking/recipe/list"
            className="cooking-main-button box-shadow thumbnail-list link"
          >
            <div className="cooking-main-button-text bold f-2">
              레시피 둘러보기
            </div>
          </Link>
          <Link
            to="/Cooking/recipe/create"
            className="cooking-main-button box-shadow thumbnail-create link"
          >
            <div className="cooking-main-button-text bold f-2">
              레시피 생성하기
            </div>
          </Link>
        </div>
      </div>
    </div>
  );
}
