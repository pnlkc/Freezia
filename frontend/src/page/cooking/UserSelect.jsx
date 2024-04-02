import { Link, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import '../../assets/styles/userselect.css';
import { getMemberList, selectUser } from '../../apis/user';
import { profileImageErrorHandler } from '../../utils/imageErrorHandler';

export default function UserSelect() {
  const [userList, setUserList] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    getMemberList().then((memberList) => {
      setUserList(memberList);
    });
  }, []);

  const login = (memberId) => {
    selectUser(memberId).then(() => {
      navigate('main');
    });
  };

  return (
    <div className="user-select-container">
      <div to="/Cooking/main" className="user-select-logo bold f-5">
        <img
          className="cooking-header-logo"
          src="/images/logo.png"
          alt="FREEZIA"
        />
        FREEZIA
      </div>
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
        {userList.map(({ imgUrl, name, memberId }) => (
          <div
            className="user-profile-box"
            key={memberId}
            onClick={() => {
              login(memberId);
            }}
          >
            <img
              className="user-profile-image"
              src={imgUrl}
              alt="사용자 사진"
              onError={profileImageErrorHandler}
            />
            <div className="user-profile-name">{name}</div>
          </div>
        ))}
      </div>
    </div>
  );
}
