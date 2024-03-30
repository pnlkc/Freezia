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
  }

  processCharacter(char, recipe, setRecipe) {
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
          this.assignValue(this.buffer, recipe, setRecipe);
        }
        this.buffer = '';
      }
    } else if (!this.inString && (char === '{' || char === '[')) {
      this.pushCurrentContext(char, recipe, setRecipe);
    } else if (!this.inString && (char === '}' || char === ']')) {
      this.popContext(recipe, setRecipe);
    } else if (!this.inString && char === ':') {
      this.isValue = true;
    } else if (!this.inString && char === ',') {
      if (this.buffer.trim() === '') return;

      if (this.isValue) {
        if (this.currentKey === '') {
          this.currentKey = this.buffer;
          this.currentKey = this.currentKey.trim();
          this.isValue = false;
        } else {
          this.assignValue(this.buffer, recipe, setRecipe);
        }
        this.buffer = '';
      } else {
        const context = this.stack[this.stack.length - 1];
        if (context.type === 'array') this.isValue = true;
      }
    } else {
      this.buffer += char;
      if (this.currentKey !== '') {
        this.addValue(
          {
            key: this.currentKey,
            value: char,
            keyStack: this.keyStack,
          },
          recipe,
          setRecipe,
        );
      }
    }
  }

  assignValue(value, recipe, setRecipe) {
    if (this.stack.length > 0) {
      const context = this.stack[this.stack.length - 1];
      if (context.type === 'array') {
        context.object.push(('' + value).trim());
        this.extendArray(recipe, setRecipe);
      } else {
        // 'object'
        context.object[this.currentKey] = ('' + value).trim();
        this.currentKey = '';
      }
      this.isValue = false;
    }
  }

  pushCurrentContext(char, recipe, setRecipe) {
    const newContext = {
      type: char === '{' ? 'object' : 'array',
      object: char === '{' ? {} : [],
    };
    if (newContext.type === 'array') {
      this.keyStack.push(this.currentKey);
      this.extendArray(recipe, setRecipe);
      this.isValue = true;
    } else this.isValue = false;

    if (this.stack.length > 0) {
      const context = this.stack[this.stack.length - 1];
      if (context.type === 'array') {
        context.object.push(newContext.object);
        if (newContext.type !== 'array') this.extendArray(recipe, setRecipe);
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

  addValue(result, recipe, setRecipe) {
    try {
      let context = recipe;
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
    } finally {
      console.log(result);
      console.log(recipe);
      setRecipe(recipe);
    }
  }

  extendArray(recipe, setRecipe) {
    let recipeContext = recipe;

    this.keyStack.forEach((key) => {
      if (recipeContext?.length > 0) {
        recipeContext = recipeContext[recipeContext.length - 1][key];
      } else recipeContext = recipeContext[key];
    });
    const lastKey = this.keyStack[this.keyStack.length - 1];
    console.log(lastKey);
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

    setRecipe(recipe);
  }

  parse({ chunk, recipe, setRecipe }) {
    for (const char of chunk) {
      this.processCharacter(char, recipe, setRecipe);
    }
  }
}

const parser = new ReplyParser();

export default parser;
