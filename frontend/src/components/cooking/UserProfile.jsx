import '../../assets/styles/cooking/userprofile.css';

export default function UserProfile() {
  const healthData = [
    {
      name: '스트레스',
      value: 80,
      maxValue: 100,
      id: 1,
      color: 'progress-red',
    },
    {
      name: '수면시간',
      value: 20,
      maxValue: 100,
      id: 2,
      color: 'progress-blue',
    },
    {
      name: '혈중산소',
      value: 30,
      maxValue: 100,
      id: 3,
      color: 'progress-green',
    },
  ];

  return (
    <div className="user-profile-container">
      <div className="user-profile-title f-2 bold">
        <span className="o-7 regular">안녕하세요 </span>
        김핑구
        <span className="o-7 regular">님</span>
      </div>
      <div className="user-health-info-box box-shadow">
        <div className="user-health-info-profile">
          <div className="sans f-1 bold">Samsung Health</div>
          <div className="user-profile-image-wrapper">
            <img
              className="user-profile-image"
              src="/images/profile/1.png"
              alt="사용자"
            />
          </div>
        </div>
        <div className="user-health-data-list ">
          {healthData.map((data) => (
            <div className="user-health-data " key={data.id}>
              <div className="user-health-data-name f-0 bold">{data.name}</div>
              <div className="user-health-data-value ">
                <progress
                  className={`user-health-data-progress ${data.color}`}
                  max={data.maxValue}
                  value={data.value}
                />
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
