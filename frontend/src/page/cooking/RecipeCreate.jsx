import { useState, useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import UserMessage from '../../components/cooking/chat/UserMessage';
import { chatExample, diseasesData, ingredientData } from '../../utils/data';

import '../../assets/styles/cooking/recipecreate.css';
import RealTimeMessage from '../../components/cooking/chat/RealTimeMessage';
import sendMessage, { registHook } from '../../apis/chat';

export default function RecipeCreate() {
  const [text, setText] = useState('');
  const [chatLog, setChatLog] = useState(
    sessionStorage.chatLog ? JSON.parse(sessionStorage.chatLog) : [],
  );
  // const [newMessage, setNewMessage] = useState({ message: '' });
  const [isProgress, setIsProgress] = useState(
    sessionStorage.isProgress === 'true',
  );
  const textareaRef = useRef(null);
  const messagesEndRef = useRef(null);
  const navigate = useNavigate();
  const [recipe, setRecipe] = useState(
    sessionStorage.replyRecipe
      ? JSON.parse(sessionStorage.replyRecipe)
      : {
          reply: '',
          recommendList: [],
          recipeList: [],
        },
  );
  const [recommendList, setRecommendList] = useState(
    sessionStorage.recommendList
      ? JSON.parse(sessionStorage.recommendList)
      : [],
  );
  let autoScroll = true;

  // useEffect(() => {
  //   const { message } = newMessage;
  //   if (message.trim() === '') return;
  //   parser.parse({ chunk: message, recipe, setRecipe, setIsProgress });
  // }, [newMessage]);

  const handleChange = (event) => {
    if (isProgress) return;
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
    sessionStorage.setItem('chatLog', JSON.stringify(chatLog));
  }, [chatLog]);

  useEffect(() => {
    registHook(setRecipe, setIsProgress);

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

      if (recipe.reply !== '' && sessionStorage.isDone === 'true') {
        setChatLog([
          ...chatLog,
          {
            recipeInfo: JSON.parse(JSON.stringify(recipe)),
            isReply: true,
            id: chatLog.length + 1,
          },
        ]);

        setRecommendList([...recipe.recommendList]);
        sessionStorage.setItem(
          'recommendList',
          JSON.stringify(recipe.recommendList),
        );

        setRecipe({
          reply: '',
          recommendList: [],
          recipeList: [],
        });

        sessionStorage.removeItem('replyRecipe');
      }
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

      // ('```json' + JSON.stringify(chatExample) + '```')
      //   .split('')
      //   .map((chunk, idx) => {
      //     setTimeout(
      //       () => {
      //         setNewMessage({ message: chunk });
      //       },
      //       idx * 30 + 1000,
      //     );
      //   });

      let scrollEvent;
      if (sessionStorage.isDone !== 'false') {
        sendMessage(url, setRecipe, setIsProgress);
        autoScroll = true;
        scrollEvent = window.addEventListener('scroll', () => {
          autoScroll = false;
        });
      }

      return () => {
        window.removeEventListener('scroll', scrollEvent);
      };
    }
  }, [isProgress]);

  useEffect(() => {
    // messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
    messagesEndRef.current?.scrollIntoView();
  }, [recipe]);

  const enterMessage = () => {
    if (isProgress) return;
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
  };

  const preventEnter = (event) => {
    if (event.key === 'Enter' && !event.shiftKey) {
      event.preventDefault();
    }
  };

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
          src={
            isProgress
              ? '/images/cooking/send_disabled.svg'
              : '/images/cooking/send.svg'
          }
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
