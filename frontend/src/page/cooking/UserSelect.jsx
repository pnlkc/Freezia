import { Link } from 'react-router-dom';
import '../../assets/styles/userselect.css';

export default function UserSelect() {
  const userList = [
    { url: '/images/profile/1.svg', name: '김싸피', id: 1 },
    { url: '/images/profile/1.svg', name: '이싸피', id: 2 },
    { url: '/images/profile/1.svg', name: '김핑구', id: 3 },
  ];

  return (
    <div className="user-select-container">
      <div className="user-select-title f-5">사용자를 선택해주세요</div>
      <div className="profile-list-container">
        <div className="profile-add-button">
          <img
            className="user-profile-image"
            src="/images/plus.svg"
            alt="사용자 추가"
          />
          <div className="user-profile-name">사용자 추가</div>
        </div>
        {userList.map(({ url, name, id }) => (
          <Link to="/Cooking/main" className="user-profile-box" key={id}>
            <img className="user-profile-image" src={url} alt="사용자 사진" />
            <div className="user-profile-name">{name}</div>
          </Link>
        ))}
      </div>
    </div>
  );
}
