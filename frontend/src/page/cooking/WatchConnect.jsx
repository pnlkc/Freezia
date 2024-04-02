import { Link, useNavigate } from 'react-router-dom';

import '../../assets/styles/cooking/watchconnect.css';

export default function WatchConnect() {
  const navigate = useNavigate();
  const recipeDetail = JSON.parse(sessionStorage.recipeInfo);

  return (
    <div
      className="watch-connect-container"
      style={{ backgroundImage: `url('${recipeDetail?.imgUrl}')` }}
    >
      <div
        className="connect-recipe-back-button f-5"
        onClick={() => {
          navigate(-1);
        }}
      >
        {'<'}
      </div>
      <div className="connect-recipe-info-box">
        <div className="connect-recipe-header-name f-4 bold">
          {recipeDetail.name}
        </div>
        <div className="connect-recipe-header-tags">
          {recipeDetail.recipeTypes.split(',').map((type) => (
            <div className="connect-recipe-header-tag" key={type}>
              {`#${type}`}
            </div>
          ))}
        </div>
      </div>
      <div className="watch-connect-button-box">
        <Link to="waiting" className="watch-connect-button orange link">
          <span className="sans">Galaxy Watch</span>
          <span>와 연동하기</span>
        </Link>
        <Link
          to="/Cooking/recipe/1/steps/0"
          className="watch-connect-button link"
          onClick={() => {
            sessionStorage.setItem('isConnected', 'false');
          }}
        >
          레시피 시작하기
        </Link>
      </div>
      <style>{'.footer-container { display : none }'}</style>
    </div>
  );
}
