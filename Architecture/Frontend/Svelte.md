# Svelte
- Frameworks are not tools for organizing your code, they're tools for organizing your mind
- Move reactivity out of the component API, and into the language

## Motivation 
- Frameworks are something that can run in your build step
See the `Problems` portion of React to see the problems with React.
- Svelte aims to dethrone React, the way React dethroned the frameworks of the early 2010s.

The assignment operator (`=`) is the easiest way to tell the computer that something changed (much easier than the whole `setState` or `useState` process of React)

```javascript
let count = 1;
count += 1;
```
is a lot simpler than
```javascript
import React, { useState } from 'react';
const [count, setCount] = useState(0);
setCount(count+1);
```

## Syntax Things
- Can use HTML elements with a variable as follows
```javascript
<script>
    let name = `here's a <strong>name</strong>`;
</script>

<p>{@html name}</p>
```

- Computing part of a component's state form another part of the state can be done as follows
```javascript
<style>
    let count = 0;
    $: doubled = count * 2; // Interpreted as "rerun this code whenever any of the referenced values change
</style>
```

- If you have a child component that has 4 props, you can use spread syntax to reference all of them
```javascript
// Info.svelte
<script>
	export let name;
	export let version;
	export let speed;
	export let website;
</script>

<p>
	The <code>{name}</code> package is {speed} fast.
	Download version {version} from <a href="https://www.npmjs.com/package/{name}">npm</a>
	and <a href={website}>learn more here</a>
</p>

// App.svelte
<script>
	import Info from './Info.svelte';

	const pkg = {
		name: 'svelte',
		version: 3,
		speed: 'blazing',
		website: 'https://svelte.dev'
	};
</script>

<Info {...pkg}/>
```

- You can simply use conditionals like so
```javascript
{#if user.loggedIn}
	<button on:click={toggle}>
		Log out
	</button>
{/if}
{#if !user.loggedIn}
	<button on:click={toggle}>
		Log in
	</button>
{/if}
```
or 
```javascript
{#if user.loggedIn}
	<button on:click={toggle}>
		Log out
	</button>
{:else}
	<button on:click={toggle}>
		Log in
	</button>
{/if}
```
**NOTE**: you can do `{:else if ...}` too

- You can easily loop over arrays or array-like objects like so
```javascript
{#each cats as cat, i}
	<li><a target="_blank" href="https://www.youtube.com/watch?v={cat.id}">
		{i + 1}: {cat.name}
	</a></li>
{/each}
```

- You can use event modifiers like `preventDefault` and others like so
```javascript
<button on:click|once={handleClick}>
	Click me
</button>
```

