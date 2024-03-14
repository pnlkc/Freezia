import Screen from './Screen';
import '../assets/styles/screen.css';
import Footer from './Footer';

export default function ScreenFrame() {
  return (
    <div className="screen-frame">
      <Screen />
      <Footer />
    </div>
  );
}
