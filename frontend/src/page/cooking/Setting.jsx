import '../../assets/styles/cooking/setting.css';
import Header from '../../components/cooking/Header';
import { diseasesData, ingredientData } from '../../utils/data';

export default function Setting() {
  const profile = JSON.parse(sessionStorage.getItem('profile'));

  const dislikeIngredients = profile.dislikeIngredients.map((id) => {
    const index = ingredientData.findIndex(
      ({ ingredientId }) => +ingredientId === +id,
    );
    return ingredientData[index].name;
  });

  const diseases = profile.diseases.map((id) => diseasesData[id]);

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
              {profile?.preferMenu.split(',').map((name) => (
                <div className="preference-setting-tag" key={name}>
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
              {dislikeIngredients.map((name) => (
                <div className="preference-setting-tag" key={name}>
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
              {diseases.map((name) => (
                <div className="preference-setting-tag" key={name}>
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
