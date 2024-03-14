import Header from '../../components/cooking/Header';
import UserProfile from '../../components/cooking/UserProfile';
import '../../assets/styles/cooking/main.css';
import RecommendRecipe from '../../components/cooking/RecommendRecipe';

export default function Main() {
  return (
    <div className="cooking-main-container">
      <Header />
      <div className="cooking-main-content-box">
        <UserProfile />
        <RecommendRecipe />
      </div>
    </div>
  );
}
