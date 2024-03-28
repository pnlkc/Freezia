import '../../assets/styles/cooking/setting.css';
import Header from '../../components/cooking/Header';

export default function Setting() {
  const data = {
    menu: [
      { id: 1, name: '한식' },
      { id: 2, name: '일식' },
      { id: 3, name: '국물요리' },
    ],
    dislike: [
      { id: 1, name: '오이' },
      { id: 2, name: '고수' },
    ],
    disease: [{ id: 1, name: '당뇨' }],
  };

  return (
    <div className="profile-setting-container">
      <Header isHome={false} />
      <div className="profile-setting-content-box">
        <div className="f-3 bold">레시피 선호도 설정</div>
        <div className="preference-setting-box box-shadow">
          <div className="preference-setting-type">
            <div className="preference-input-wrapper">
              <input
                className="preference-input"
                placeholder="이러한 종류의 음식을 선호해요"
              />
              <img
                className="preference-input-icon"
                src="/images/cooking/plus_circle.svg"
                alt="추가 버튼 "
              />
            </div>
            <div className="preference-settting-tags">
              {data.menu.map(({ id, name }) => (
                <div className="preference-setting-tag" key={id}>
                  <span>{name}</span>
                  <img
                    className="preference-setting-tag-icon"
                    src="/images/cooking/delete_white.svg"
                    alt="삭제"
                  />
                </div>
              ))}
            </div>
          </div>
          <div className="preference-setting-type">
            <div className="preference-input-wrapper">
              <input
                className="preference-input"
                placeholder="이러한 재료는 피하고 싶어요"
              />
              <img
                className="preference-input-icon"
                src="/images/cooking/plus_circle.svg"
                alt="추가 버튼 "
              />
            </div>
            <div className="preference-settting-tags">
              {data.dislike.map(({ id, name }) => (
                <div className="preference-setting-tag" key={id}>
                  <span>{name}</span>
                  <img
                    className="preference-setting-tag-icon"
                    src="/images/cooking/delete_white.svg"
                    alt="삭제"
                  />
                </div>
              ))}
            </div>
          </div>
          <div className="preference-setting-type">
            <div className="preference-input-wrapper">
              <input
                className="preference-input"
                placeholder="이러한 지병을 가지고 있어요"
              />
              <img
                className="preference-input-icon"
                src="/images/cooking/plus_circle.svg"
                alt="추가 버튼 "
              />
            </div>
            <div className="preference-settting-tags">
              {data.disease.map(({ id, name }) => (
                <div className="preference-setting-tag" key={id}>
                  <span>{name}</span>
                  <img
                    className="preference-setting-tag-icon"
                    src="/images/cooking/delete_white.svg"
                    alt="삭제"
                  />
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
      <div className="preference-save-button box-shadow">저장</div>
    </div>
  );
}
