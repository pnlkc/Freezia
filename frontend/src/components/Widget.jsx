import '../assets/styles/widget.css';

export default function Widget() {
  return (
    <div className="widget-container">
      <span className="widget-title sans f-0 bold">SmartThings Cooking</span>
      <div className="widget-card" style={{ height: '25%' }} />
      <div className="widget-card" style={{ height: '30%' }} />
      <div className="widget-card" style={{ height: '40%' }} />
      <div className="widget-navbar">
        <div className="widget-selected-menu">홈</div>
        <div>검색</div>
        <div>커뮤니티</div>
        <div>MY</div>
      </div>
    </div>
  );
}
