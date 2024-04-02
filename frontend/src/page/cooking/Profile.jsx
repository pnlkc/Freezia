import { Link } from 'react-router-dom';
import '../../assets/styles/cooking/profile.css';
import Header from '../../components/cooking/Header';
import PreferenceBox from '../../components/cooking/PreferenceBox';
import UserProfile from '../../components/cooking/UserProfile';

export default function Profile() {
  return (
    <div className="profile-container">
      <Header isHome={false} />
      <div className="profile-content-container">
        <UserProfile />
        <PreferenceBox />
        <div className="recommend-recipe-title f-2">
          <span className="profile-list bold">목록</span>
        </div>
        <Link
          to="/Cooking/profile/history"
          className="profile-link box-shadow link"
        >
          <span className="c-p f-2 bold">레시피 히스토리</span>
          <span className="c-b o-7"> 로 이동하기</span>
        </Link>
        <Link
          to="/Cooking/profile/shopping"
          className="profile-link box-shadow link"
        >
          <span className="c-p f-2 bold">쇼핑 리스트</span>
          <span className="c-b o-7"> 로 이동하기</span>
        </Link>
      </div>
    </div>
  );
}
