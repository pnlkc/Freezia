const startRecognition = () => {
  const r = document.getElementById('result');
  if ('webkitSpeechRecognition' in window) {
    const speechRecognizer = new window.webkitSpeechRecognition();
    speechRecognizer.continuous = true;
    speechRecognizer.interimResults = true;
    speechRecognizer.lang = 'ko-KR';
    speechRecognizer.start();

    let finalTranscripts = '';

    let mediaStream;
    let mediaRecorder;

    navigator.mediaDevices.getUserMedia({ audio: true }).then((stream) => {
      mediaStream = stream;

      let chunks = [];
      mediaRecorder = new MediaRecorder(mediaStream);

      mediaRecorder.onstart = () => {
        chunks = [];
      };

      mediaRecorder.ondataavailable = (e) => {
        chunks.push(e.data);
      };

      mediaRecorder.onstop = () => {
        const blob = new Blob(chunks, {
          type: 'audio/ogg; codecs=opus',
        });
        console.log(blob);
        chunks = [];
      };

      mediaRecorder.start();
    });

    speechRecognizer.onresult = (event) => {
      let interimTranscripts = '';
      for (let i = event.resultIndex; i < event.results.length; i += 1) {
        const { transcript } = event.results[i][0];
        // recognizeUser(transcript)
        transcript.replace('\n', '<br>');
        if (event.results[i].isFinal) {
          finalTranscripts = transcript;
          mediaRecorder.stop();
          mediaRecorder.start();
        } else {
          interimTranscripts += transcript;
        }
        r.innerHTML = `${finalTranscripts} <span style="color: #999;"> ${interimTranscripts} </span>`;
      }
    };
  } else {
    r.innerHTML = 'Your browser does not support that.';
  }
};

export default startRecognition;

export const speak = () => {
  const t = document.getElementById('input');
  if ('SpeechSynthesisUtterance' in window) {
    const utterance = new SpeechSynthesisUtterance(t.value);
    window.speechSynthesis.speak(utterance);
  } else {
    t.value = 'Your browser does not support that.';
  }
};

export const recognizeUser = (text) => {
  const keyword = '빅스비';
  const re = new RegExp(keyword);
  if (text.search(re) < 0) return true;
  return false;
};
