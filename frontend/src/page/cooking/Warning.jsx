import { useNavigate } from 'react-router-dom';
import '../../assets/styles/cooking/warning.css';

export default function Warning() {
  const navigate = useNavigate();
  const { description, imgUrl, name, disease } = JSON.parse(
    sessionStorage.getItem('warning'),
  );
  // const disease = '지병';

  return (
    <div className="warning-container">
      <img
        className="close-warning"
        src="/images/cooking/delete_white.svg"
        alt="종료"
        onClick={() => {
          navigate(-1);
        }}
      />
      <img
        className="warning-icon"
        src="/images/cooking/warn.svg"
        alt="위험 음식 알림"
      />
      <img className="warning-ingredient-image" src={imgUrl} alt={name} />
      <div className="warning-title">
        <span className="bold">{name}</span>
        <span className="o-9"> 는 </span>
        <span className="bold">{disease} </span>
        <span className="o-9">에 좋지 않아요!</span>
      </div>
      <div className="warning-description">{description}</div>
    </div>
  );
}
