import { useState, useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { EventSourcePolyfill, NativeEventSource } from 'event-source-polyfill';

import UserMessage from '../../components/cooking/chat/UserMessage';
import { chatExample, diseasesData, ingredientData } from '../../utils/data';
import parser from '../../utils/replyParser';

import '../../assets/styles/cooking/recipecreate.css';
import RealTimeMessage from '../../components/cooking/chat/RealTimeMessage';

export default function RecipeCreate() {
  const [text, setText] = useState('');
  const [chatLog, setChatLog] = useState([]);
  const [newMessage, setNewMessage] = useState({ message: '' });
  const [isProgress, setIsProgress] = useState(false);
  const textareaRef = useRef(null);
  const messagesEndRef = useRef(null);
  const navigate = useNavigate();
  const [reply, setReply] = useState('답변을 기다리고 있습니다...');
  const [recipe, setRecipe] = useState({
    reply: '',
    recommendList: [],
    recipeList: [],
  });
  const [recommendList, setRecommendList] = useState([]);

  useEffect(() => {
    const { message } = newMessage;
    if (message.trim() === '') return;
    setReply(reply + message);
    parser.parse({ chunk: message, recipe, setRecipe, setIsProgress });
  }, [newMessage]);

  const handleChange = (event) => {
    if (event.key === 'Enter' && !event.shiftKey) {
      textareaRef.current.value = '';
      setText('');
      return;
    }
    setText(event.target.value);
  };

  useEffect(() => {
    if (textareaRef.current) {
      textareaRef.current.style.height = '2.6vw'; // 높이를 초기화
    }
  }, [text]); // text 상태가 변경될 때마다 높이 조정

  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [chatLog]);

  useEffect(() => {
    const event = window.addEventListener('resize', () => {
      if (textareaRef.current) {
        textareaRef.current.style.height = '2.6vw'; // 높이를 초기화
        textareaRef.current.style.height = `${textareaRef.current.scrollHeight}px`;
      }
    });

    return () => {
      window.removeEventListener('resize', event);
    };
  }, []);

  useEffect(() => {
    if (!isProgress) {
      if (chatLog.length === 0) return;
      messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });

      setChatLog([
        ...chatLog,
        {
          recipeInfo: JSON.parse(JSON.stringify(recipe)),
          isReply: true,
          id: chatLog.length + 1,
        },
      ]);

      setRecommendList([...recipe.recommendList]);

      setRecipe({
        reply: '',
        recommendList: [],
        recipeList: [],
      });
    } else {
      const userInfo = JSON.parse(sessionStorage.getItem('profile'));
      const diseases = userInfo.diseases
        .map((id) => diseasesData[id])
        .join(', ');

      const dislikeIngredients = userInfo.dislikeIngredients
        .map((id) => {
          const index = ingredientData.findIndex(
            ({ ingredientId }) => +ingredientId === +id,
          );
          return ingredientData[index].name;
        })
        .join(', ');

      const ingredientList = JSON.parse(sessionStorage.getItem('ingredients'));
      const ingredients = ingredientList.map(({ name }) => name).join(', ');

      const url = `generate-AI?ingredients=${ingredients}&diseases=${diseases}&dislikeIngredients=${dislikeIngredients}&prompt=${textareaRef.current.value}`;

      textareaRef.current.value = '';
      textareaRef.current.style.height = '2.6vw';
      textareaRef.current.style.height = `${textareaRef.current.scrollHeight}px`;

      ('```json' + JSON.stringify(chatExample) + '```')
        .split('')
        .map((chunk, idx) => {
          setTimeout(
            () => {
              setNewMessage({ message: chunk });
            },
            idx * 30 + 1000,
          );
        });

      return;

      const EventSource = EventSourcePolyfill || NativeEventSource;

      const eventSource = new EventSource(
        `${import.meta.env.VITE_BASE_URL}/${url}`,
        {
          headers: {
            Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`,
          },
        },
      );

      eventSource.onmessage = (event) => {
        setNewMessage({ message: event.data });
      };

      eventSource.onerror = (event) => {
        eventSource.close();
        setIsProgress(false);
      };

      return () => {
        eventSource.close();
      };
    }
  }, [isProgress]);

  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [recipe]);

  const enterMessage = () => {
    if (textareaRef.current.value.trim() === '') return;

    setIsProgress(true);

    setChatLog([
      ...chatLog,
      {
        chat: textareaRef.current.value,
        isReply: false,
        id: chatLog.length + 1,
      },
    ]);

    textareaRef.current.value = '';
    textareaRef.current.style.height = '2.6vw';
    textareaRef.current.style.height = `${textareaRef.current.scrollHeight}px`;
  };

  const preventEnter = (event) => {
    if (event.key === 'Enter' && !event.shiftKey) {
      event.preventDefault();
    }
  };

  const recommendChat = [
    '가장 최근에 만든 요리와 비슷한 재료를 사용하는 레시피를 알려줘',
    '간식으로 먹고 싶은데 칼로리를 절반으로 줄인 레시피를 알려줘',
    '덮밥말고 볶음밥 레시피로 알려줘',
  ];

  const selectRecommend = (chat) => {
    textareaRef.current.value = chat;
    setText(chat);
    enterMessage();
  };

  return (
    <div className="recipe-create-container">
      <div
        className="reipce-create-back-button f-5"
        onClick={() => {
          navigate(-1);
        }}
      >
        {'<'}
      </div>
      <div className="recipe-create-chat-container">
        {chatLog.map(({ chat, isReply, recipeInfo, id }) => {
          return isReply ? (
            <RealTimeMessage recipe={recipeInfo} key={id} />
          ) : (
            <UserMessage chat={chat} key={id} />
          );
        })}
        {isProgress && <RealTimeMessage recipe={recipe} />}
        {chatLog.length > 0 &&
          chatLog[chatLog.length - 1].isReply &&
          !isProgress &&
          recommendList.map((chat) => (
            <div
              className="recommend-chat-message f-0"
              key={chat}
              onClick={() => {
                selectRecommend(chat);
              }}
            >
              {chat}
            </div>
          ))}
        <div ref={messagesEndRef} />
      </div>
      <div className="recipe-create-input-wrapper">
        <textarea
          ref={textareaRef}
          onChange={handleChange}
          className="recipe-create-input f-1"
          placeholder="채팅 내용을 입력하세요"
          onKeyUp={(event) => {
            if (event.key === 'Enter' && !event.shiftKey) {
              enterMessage();
            }
          }}
          onKeyDown={preventEnter}
        />
        <img
          src="/images/cooking/send.svg"
          className="recipe-create-send-icon"
          alt="대화 전송하기"
          onClick={() => {
            enterMessage();
          }}
        />
      </div>
    </div>
  );
}
