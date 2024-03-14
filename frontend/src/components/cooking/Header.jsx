import '../../assets/styles/cooking/header.css';

export default function Header() {
  return (
    <div className="cooking-header">
      <div className="cooking-header-title sans f-3">SmartThings Cooking</div>
      <div className="cooking-header-icon-box">
        <img
          className="cooking-header-icon"
          src="/images/cooking/bookmark.svg"
          alt="북마크"
        />
        <img
          className="cooking-header-icon"
          src="/images/cooking/notification.svg"
          alt="북마크"
        />
        <img
          className="cooking-header-profile"
          src="/images/profile/1.svg"
          alt="사용자"
        />
      </div>
    </div>
  );
}
