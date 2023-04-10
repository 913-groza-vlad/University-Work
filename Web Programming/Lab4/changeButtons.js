const changeBackgroundButton = document.getElementById('change-background-button');
const changeLinkStyleButton = document.getElementById('change-link-style-button');

// Define the background images to rotate through
const backgrounds = [
  'images/mountains-1.jpg',
  'images/mountains-2.webp',
  'images/beach-1.webp',
  'images/beach-2.jpg',
  'images/sunset.jpg'
];

// Define the link styles to rotate through
const linkStyles = [
  { color: 'orange', textDecoration: 'underline', fontFamily: 'Arial', textShadow: '2px 2px 5px black' },
  { color: 'green', textDecoration: 'none', fontFamily: 'Times New Roman', textShadow: '1px 1px 1px white' },
  { color: 'yellow', textDecoration: 'line-through', fontFamily: 'Courier New', textShadow: '4px 1px 2px orange' },
  { color: 'lightblue', textDecoration: 'underline overline', fontFamily: 'Helvetica', textShadow: '3px 3px 5px white' },
];

// Set up a variable to track the current background and link style
let currentBackgroundIndex = 0;
let currentLinkStyleIndex = 0;

// Add event listeners to the buttons
changeBackgroundButton.addEventListener('click', () => {
  currentBackgroundIndex = (currentBackgroundIndex + 1) % backgrounds.length;
  document.body.style.backgroundImage = `url(${backgrounds[currentBackgroundIndex]})`;
});

changeLinkStyleButton.addEventListener('click', () => {
  currentLinkStyleIndex = (currentLinkStyleIndex + 1) % linkStyles.length;
  var links = document.getElementsByTagName('a');
  for (let i = 0; i < links.length; i++) {
    var link = links[i];
    link.style.color = linkStyles[currentLinkStyleIndex].color;
    link.style.textDecoration = linkStyles[currentLinkStyleIndex].textDecoration;
    link.style.fontFamily = linkStyles[currentLinkStyleIndex].fontFamily;
    link.style.textShadow = linkStyles[currentLinkStyleIndex].textShadow;
  }
});