radio86-soft-remake
===================

Симулятор бейсика и псевдографики ретрокомпьютера Радио 86РК

Программирование на JavaScript c дополнительными функциями похожими на функции языка бейсик ретрокомпьютера Радио 86РК.

Radio 86RK retrocomputer simulator (BASIC operators using JavaScript, pseudo-graphics output).

Just for fun ... наподобие очень примитивного псевдографического Скретча, максимальная простота программирования/конструкций.

Работающие операторы и ф-ии: 

* plot(x,y,1) // псевдографика;
* plot(x,y,0)
* line(x,y)

* cls()

* print(message)
* println(message)
* printtab(x) // аналог print tab(x)

* spc(n) // аналог spc(n)

* result = input(message) // пока что ввод в отдельном окошке с последующим выводом в консоль;

* result = inkey(0) // ждет ввода символа с клавиатуры;
* result = inkey(1) // не ждет ввода;

* cur(x,y)
* result = screen(x,y)

* pause(0) // ждет нажатия любой клавиши с клавиатуры;
* pause(double) // в секундах или в долях секунды: 1, 10, 0.5, и т.д.

* cos(radians)
* sin(radians)

* n = chr(char)
* char = asc(n)

* poke(addr, n) // эти команды нужны в старых программах для прямого доступа к экранной памяти
* n = peek(addr)

Дополнительные ф-ии:

* circle(x, y, r)
* log(message)

* freeze() // останов отрисовки экрана, чтобы быстро вывести много символов в экранную область;
* unfreeze() // возобновление отрисовки экрана;

* f = localFile(location, fileName); a = f.asArray();
* f = remoteFile(path); a = f.asArray();

TODO: если еще даст Бог милость пожить и будет время и настроение

* мобильная версия Android или же web-версия;
* загрузка программ по URL, git gist;
* координатная плоскость (4 области) + цвета, для школьного курса математики;
* использование BeanShell (Java scripting), возможно ли использовать Truffle;
* интерпретатор BASIC;

===================

ru.wikipedia.org/wiki/Радио_86РК

===================

github.com/igorkovalchuk/radio86-soft-remake/wiki (скриншоты)

===================
