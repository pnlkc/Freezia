import { useNavigate } from 'react-router-dom';
import '../../assets/styles/cooking/warning.css';

export default function Warning() {
  const navigate = useNavigate();

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
      <img
        className="warning-ingredient-image"
        src="/images/cooking/peach.svg"
        alt="복숭아"
      />
      <div className="warning-title">
        <span className="bold">복숭아 </span>
        <span className="o-9">는 </span>
        <span className="bold">당뇨환자 </span>
        <span className="o-9">에게 좋지 않아요!</span>
      </div>
      <div className="warning-description">
        복숭아는 육질이 부드럽고 당분이 많아 혈당을 빠르게 올릴 수 있습니다.
      </div>
    </div>
  );
}
