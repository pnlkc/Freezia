import { Link } from 'react-router-dom';
import { diseasesData, ingredientData } from '../../utils/data';

export default function PreferenceBox() {
  const profile = JSON.parse(sessionStorage.getItem('profile'));

  const dislikeIngredients = profile.dislikeIngredients.map((id) => {
    const index = ingredientData.findIndex(
      ({ ingredientId }) => +ingredientId === +id,
    );
    return ingredientData[index].name;
  });

  const diseases = profile.diseases.map((id) => diseasesData[id]);

  return (
    <div className="recommend-recipe-container">
      <div className="preference-recipe-title f-2 bold">
        <span>레시피 선호도</span>
        <Link to="setting">
          <img
            className="preference-setting-icon"
            src="/images/cooking/setting.svg"
            alt="설정"
          />
        </Link>
      </div>
      <div className="preference-contents-box box-shadow">
        <div className="prefrence-menu-box">
          <div className="preference-type">선호 메뉴</div>
          <div className="preference-tags">
            {profile?.preferMenu.split(',').map((menu) => (
              <div className="preference-tag">{menu.trim()}</div>
            ))}
          </div>
        </div>
        <div className="prefrence-menu-box">
          <div className="preference-type">기피 식재료</div>
          <div className="preference-tags">
            {dislikeIngredients.map((ingredient) => (
              <div className="preference-tag">{ingredient}</div>
            ))}
          </div>
        </div>
        <div className="prefrence-menu-box">
          <div className="preference-type">지병</div>

          <div className="preference-tags">
            {diseases.map((disease) => (
              <div className="preference-tag">{disease}</div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
