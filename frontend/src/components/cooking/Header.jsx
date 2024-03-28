import { Link, useNavigate } from 'react-router-dom';
import '../../assets/styles/cooking/header.css';
import { profileImageErrorHaner } from '../../utils/imageErrorHandler';

export default function Header({ isHome }) {
  const navigate = useNavigate();

  return (
    <div className="cooking-header">
      {!isHome ? (
        <div
          className="header-back-button f-4 link"
          onClick={() => {
            navigate(-1);
          }}
        >
          {'<'}
        </div>
      ) : (
        <Link to="/Cooking/main" className="cooking-header-title sans f-3 link">
          SmartThings Cooking
        </Link>
      )}
      <div className="cooking-header-icon-box">
        <img
          className="cooking-header-icon"
          src="/images/cooking/bookmark.svg"
          alt="북마크"
        />
        <img
          className="cooking-header-icon"
          src="/images/cooking/notification.svg"
          alt="알림"
        />
        <Link to="/Cooking/profile" className="cooking-header-profile">
          <img
            className="cooking-header-profile"
            src={JSON.parse(sessionStorage.profile).imgUrl}
            alt="사용자"
            onError={profileImageErrorHaner}
          />
        </Link>
      </div>
    </div>
  );
}
