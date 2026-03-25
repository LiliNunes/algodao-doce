const fs = require('fs');
let text = fs.readFileSync('index.html', 'utf8');
const lines = text.split('\n');
// Remover da linha 846 até a 998. (Indices 845 a 997)
lines.splice(845, 153);
fs.writeFileSync('index.html', lines.join('\n'));
console.log('Removed isAccountOpen drawer from index.html');
