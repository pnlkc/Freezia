import '../../assets/styles/cooking/userprofile.css';

export default function UserProfile() {
  const userInfo = JSON.parse(sessionStorage.getItem('profile'));

  const healthData = [
    {
      name: '스트레스',
      value: userInfo.stress,
      maxValue: 10,
      id: 'stress',
      color: 'progress-red',
      suffix: '',
    },
    {
      name: '수면시간',
      value: userInfo.sleep,
      maxValue: 10,
      id: 'sleep',
      color: 'progress-blue',
      suffix: ' 시간',
    },
    {
      name: '혈중산소',
      value: userInfo.bloodOxygen,
      maxValue: 15,
      id: 'bloodOxygen',
      color: 'progress-green',
      suffix: ' %',
    },
  ];

  const selectIcon = (value) => {
    if (value <= 3) return '😀';
    else if (value <= 7) return '😐';
    return '😢';
  };

  return (
    <div className="user-profile-container">
      <div className="user-profile-title f-2 bold">
        <span className="o-7 regular">안녕하세요 </span>
        {userInfo.name}
        <span className="o-7 regular">님</span>
      </div>
      <div className="user-health-info-box box-shadow">
        <div>
          <span className="sans f-2 bold">Samsung Health</span>
        </div>
        {/* <div className="user-health-info-profile">
          <div className="user-profile-image-wrapper">
            <img
              className="user-profile-image"
              src={userInfo.imgUrl}
              alt="사용자"
              onError={profileImageErrorHaner}
            />
          </div>
        </div> */}
        <div className="user-health-data-list ">
          {healthData.map((data) => (
            <div className="user-health-data" key={data.id}>
              <div className="user-health-data-name f-1 bold">{data.name}</div>
              <div className="user-health-data-value ">
                {/* <progress
                  className={`user-health-data-progress ${data.color}`}
                  max={data.maxValue}
                  value={data.value}
                /> */}
                <div className="user-health-data-info-wrapper">
                  <div
                    className={`user-health-data-info ${data.id}`}
                    style={{
                      left: `${((data.id === 'bloodOxygen' ? data.value - 85 : data.value) / data.maxValue) * 100}%`,
                    }}
                  >
                    {`${data.id === 'stress' ? selectIcon(data.value) : data.value}${data.suffix}`}
                  </div>
                </div>
                <div className={`user-health-data-progress-wrapper ${data.id}`}>
                  <div
                    className={`user-health-data-progress-bar ${data.id}`}
                    style={{
                      left: `${((data.id === 'bloodOxygen' ? data.value - 85 : data.value) / data.maxValue) * 100}%`,
                    }}
                  />
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
