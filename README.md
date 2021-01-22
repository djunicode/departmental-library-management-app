<p>
    <h1 align='center'> 📚 Departmental Library App </h1>
</p>

<h4 align='center'> Repository for the Unicode 2021 project for Departmental Library App. </h4>

<br>
<br>
<br>

## File Structure

```
.
├── LICENSE
├── README.md
├── libraryAndroid/ -> Android app for the project
└── libraryBackend/ -> Django backend for the endpoints
	├── libraryApp/ -> Django app
	├── libraryProject -> Project configurations
	├── manage.py
	└── requirements.txt
```

## Technology Stack

#### Backend
- Django 3.0+ (Python 3.6+)
- Django REST Framework
- Djoser authentication library

<!--- #### Android -->

## Build Instructions

#### Backend
```bash
  pip3 install -r requirements.txt
  python3 manage.py makemigrations
  python3 manage.py migrate
  python3 manage.py runserver
```

<!-- #### Android
```bash
```
-->

## Development Instructions

1. Before adding or commiting to git, please run `black .` inside `libraryBackend`. This is important because we are using Black code formatter for this project

2. The database we are using is sqlite3 for the prototype. We may change it to PostgreSQL later.

<!-- 3. Add Android instructions. -->

#### Developers

1. Junaid Girkar (Backend)
2. Kartik Suvarna (Backend)
3. Shreyas Rami (Backend)
4. Aditi Kamath (Android)
5. Aryan Pathare (Android)
6. Shubhechha (Android)
7. Karishni Mehta (Design)

#### Mentors

1. Harsh Vartak (Backend)
2. Sakshi Uppoor (Backend)
3. Bhavi Dave (Android)
4. Raj Shah (Android)
5. Punit Lodha (Design)

## License

> MIT License
>
> Copyright (c) 2021 Unicode
>
> Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
>
> THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
