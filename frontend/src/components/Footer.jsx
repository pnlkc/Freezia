import '../assets/styles/footer.css';

export default function Footer() {
  // return <img className="footer-image" src="/images/footer.png" alt="footer" />;
  const iconList = [
    '/images/bixby.png',
    '/images/task.png',
    '/images/home.png',
    '/images/back.png',
    '/images/alarm.png',
  ];

  return (
    <div className="footer-container">
      <div className="footer-icon-box">
        {iconList.map((url) => (
          <img className="footer-icon" src={url} alt={url} key={url} />
        ))}
      </div>
    </div>
  );
}
