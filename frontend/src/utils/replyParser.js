class ReplyParser {
  constructor() {
    this.reset();
  }

  reset() {
    this.stack = [];
    this.currentObject = {};
    this.currentKey = '';
    this.buffer = '';
    this.inString = false;
    this.isEscaped = false;
    this.isValue = false;
    this.keyStack = [];
    this.total = '';
    this.recipe = {
      reply: '',
      recommendList: [],
      recipeList: [],
    };
  }

  processCharacter(char) {
    if (char !== '{' && this.stack.length === 0) return;
    this.total += char;

    if (this.isEscaped) {
      this.buffer += char;
      this.isEscaped = false;
    } else if (char === '\\') {
      this.isEscaped = true;
    } else if (char === '"') {
      this.inString = !this.inString;
      if (!this.inString) {
        if (this.currentKey === '') {
          this.currentKey = this.buffer;
          this.currentKey = this.currentKey.trim();
        } else {
          this.assignValue(this.buffer);
        }
        this.buffer = '';
      }
    } else if (!this.inString && (char === '{' || char === '[')) {
      this.pushCurrentContext(char);
    } else if (!this.inString && (char === '}' || char === ']')) {
      this.popContext();
    } else if (!this.inString && char === ':') {
      this.isValue = true;
    } else if (!this.inString && char === ',') {
      const context = this.stack[this.stack.length - 1];
      if (context.type === 'array') this.extendArray();
      if (this.buffer.trim() === '') return;

      if (this.isValue) {
        if (this.currentKey === '') {
          this.currentKey = this.buffer;
          this.currentKey = this.currentKey.trim();
          this.isValue = false;
        } else {
          this.assignValue(this.buffer);
        }
        this.buffer = '';
      } else {
        const context = this.stack[this.stack.length - 1];
        if (context.type === 'array') this.isValue = true;
      }
    } else {
      this.buffer += char;
      if (this.currentKey !== '') {
        this.addValue({
          key: this.currentKey,
          value: char,
          keyStack: this.keyStack,
        });
      }
    }
  }

  assignValue(value) {
    if (this.stack.length > 0) {
      const context = this.stack[this.stack.length - 1];
      if (context.type === 'array') {
        context.object.push(('' + value).trim());
      } else {
        // 'object'
        context.object[this.currentKey] = ('' + value).trim();
        this.currentKey = '';
      }
      this.isValue = false;
    }
  }

  pushCurrentContext(char) {
    const newContext = {
      type: char === '{' ? 'object' : 'array',
      object: char === '{' ? {} : [],
    };
    if (newContext.type === 'array') {
      this.keyStack.push(this.currentKey);
      this.extendArray();
      this.isValue = true;
    } else this.isValue = false;

    if (this.stack.length > 0) {
      const context = this.stack[this.stack.length - 1];
      if (context.type === 'array') {
        context.object.push(newContext.object);
      } else {
        context.object[this.currentKey] = newContext.object;
      }
      if (newContext.type === 'object') this.currentKey = '';
    }

    this.stack.push(newContext);
    this.currentObject = newContext.object;
  }

  popContext() {
    const finishedContext = this.stack.pop();
    if (finishedContext.type === 'array') {
      this.keyStack.pop();
      this.isValue = false;
      this.currentKey = '';
    }
    this.currentObject =
      this.stack.length > 0 ? this.stack[this.stack.length - 1].object : {};

    if (this.stack.length === 0) {
      // At this point, finishedContext.object contains the fully parsed object or array
      console.log('Finished parsing:', finishedContext.object);
      console.log(this.total);
      try {
        console.log(JSON.parse(this.total));
      } catch (e) {
        console.error(e);
      }
    }
  }

  addValue(result) {
    try {
      let context = this.recipe;
      result.keyStack.forEach((key) => {
        if (context?.length > 0) context = context[context.length - 1][key];
        else context = context[key];
      });
      if (result.keyStack.length === 0) {
        context[result.key] += result.value;
      } else {
        if (typeof context[context.length - 1] === 'object') {
          context[context.length - 1][result.key] += result.value;
        } else context[context.length - 1] += result.value;
      }
    } catch (error) {
      // console.log(result);
      // console.log(recipe);
      console.error(error);
    } finally {
      sessionStorage.setItem('replyRecipe', JSON.stringify(this.recipe));
    }
  }

  extendArray() {
    let recipeContext = this.recipe;

    this.keyStack.forEach((key) => {
      if (recipeContext?.length > 0) {
        recipeContext = recipeContext[recipeContext.length - 1][key];
      } else recipeContext = recipeContext[key];
    });
    const lastKey = this.keyStack[this.keyStack.length - 1];
    if (lastKey === 'ingredientList' || lastKey === 'seasoningList') {
      recipeContext.push({ name: '', amounts: '', unit: '' });
    } else if (lastKey === 'recipeSteps') {
      recipeContext.push({
        type: '',
        description: '',
        name: '',
        duration: '',
        tip: '',
      });
    } else if (lastKey === 'recipeList') {
      recipeContext.push({
        calorie: '',
        cookTime: '',
        ingredientList: [],
        name: '',
        recipeSteps: [],
        recipeType: '',
        seasoningList: [],
        servings: '',
      });
    } else {
      recipeContext.push('');
    }
  }

  parse(chunk) {
    for (const char of chunk) {
      this.processCharacter(char);
    }
  }
}

const parser = new ReplyParser();

export default parser;
