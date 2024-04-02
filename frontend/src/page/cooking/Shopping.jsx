import { useEffect, useState } from 'react';

import Header from '../../components/cooking/Header';

import '../../assets/styles/cooking/shopping.css';

export default function Shopping() {
  const [shoppingList, setShoppingList] = useState(
    sessionStorage.getItem('shoppingList')
      ? JSON.parse(sessionStorage.getItem('shoppingList'))
      : [],
  );

  const checkItem = (itemId) => {
    setShoppingList(
      shoppingList.map(({ shoppingListId, checkYn, name }) => {
        if (shoppingListId === itemId) {
          return { shoppingListId, checkYn: !checkYn, name };
        }
        return { shoppingListId, checkYn, name };
      }),
    );
  };

  const removeCheckedItem = () => {
    setShoppingList(shoppingList.filter(({ checkYn }) => !checkYn));
  };

  useEffect(() => {
    sessionStorage.setItem('shoppingList', JSON.stringify(shoppingList));
  }, [shoppingList]);

  return (
    <div className="profile-shopping-container">
      <Header />
      <div className="profile-shopping-content-container">
        <div className="profile-shopping-title">
          <img
            className="profile-shopping-title-icon"
            src="/images/cooking/shopping.svg"
            alt="쇼핑리스트"
          />
          <span className="f-3 bold">쇼핑 리스트</span>
        </div>
        <div className="shopping-list-container box-shadow">
          <div className="shopping-list-box">
            <span className="f-2 bold">
              {`${
                shoppingList.filter(({ checkYn }) => !checkYn).length
              }개의 아이템`}
            </span>
            <img
              className="shopping-add-icon"
              src="/images/cooking/add.svg"
              alt="아이템 추가"
            />
          </div>
          <div className="shopping-list-wrapper">
            {shoppingList
              .filter(({ checkYn }) => !checkYn)
              .map(({ shoppingListId, name }) => (
                <div className="shoppping-list-item" key={shoppingListId}>
                  <span className="shopping-list-name">{name}</span>
                  <img
                    className="shopping-list-checkbox"
                    src="/images/cooking/shopping/checkbox_empty.svg"
                    alt="선택"
                    onClick={() => {
                      checkItem(shoppingListId);
                    }}
                  />
                </div>
              ))}
          </div>
          <div className="shopping-list-box">
            <span className="f-2 bold">체크된 아이템</span>
            <span
              className="shopping-list-delete-button f-0"
              onClick={() => {
                removeCheckedItem();
              }}
            >
              지우기
            </span>
          </div>
          <div className="shopping-delete-list-wrapper">
            {shoppingList
              .filter(({ checkYn }) => checkYn)
              .map(({ shoppingListId, name }) => (
                <div className="shoppping-list-item" key={shoppingListId}>
                  <span className="shopping-delete-list-name">{name}</span>
                  <img
                    className="shopping-list-checkbox"
                    src="/images/cooking/shopping/checkbox_checked.svg"
                    alt="선택"
                    onClick={() => {
                      checkItem(shoppingListId);
                    }}
                  />
                </div>
              ))}
          </div>
        </div>
      </div>
    </div>
  );
}
