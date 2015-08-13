[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Android%20Json%20Wizard-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1848)


Android Json Wizard
=========

Android Json Wizard is a library for creating beautiful form based wizards within your app just by defining json in a particular format.

# Demo
[![alt demo](http://img.youtube.com/vi/0PQD8EA8lEI/hqdefault.jpg)](https://www.youtube.com/watch?v=0PQD8EA8lEI)

[Demo Youtube Link](http://youtu.be/0PQD8EA8lEI)

# Usage

## Json Structure

Form json should consist of steps and fields.

## Steps

Step directly corresponds to a fragment(or a page) in wizard. It consists of different fields(array of fields), title and next step.

```json
    {
     "step1":{
             "fields":[
                 {
                     "key":"name",
                     "type":"edit_text",
                     "hint":"Enter Your Name"
                 },
                 {
                     "key":"email",
                     "type":"edit_text",
                     "hint":"Enter email address"
                 },
                 {
                     "key":"labelBackgroundImage",
                     "type":"label",
                     "text":"Choose Background Image"
                 },
                 {
                     "key":"chooseImage",
                     "type":"choose_image",
                     "uploadButtonText":"Choose"
                 }
             ],
             "title":"Step 1",
             "next":"step2"
         }
    }
```

## Supported fields

#### EditText
```json
    {
        "key":"name",
        "type":"edit_text",
        "hint":"Enter Your Name"
    }
```

key - must be unique in that particular step.

type - must be edit_text for EditText.

hint - hint for EditText.

value - will be the value present in the editText after completion of wizard

##### EditText Required Validation

```json
"v_required" : {
                    "value" : "true",
                    "err" : "Please enter some value this is a required field."
               }
```

##### EditText Min length Validation

```json
"v_min_length" : {
                    "value" : "3",
                    "err" : "Min length should be at least 3"
                }
```

##### EditText Max Length Validation

```json
"v_max_length" : {
                    "value" : "30",
                    "err" : "Max length is 30"
                }
```

##### EditText Email Validation

```json
"v_email" : {
                    "value" : "true",
                    "err" : "Not an email."
                }
```

##### EditText Regex Validation

```json
"v_email" : {
                    "value" : "your-regex-here",
                    "err" : "Your message here."
                }
```

#### Label
```json
    {
     "key":"labelHeaderImage",
     "type":"label",
     "text":"Choose Background Image"
    }
```
key - must be unique in that particular step.

type - must be label for Label.

text - text for Label.


#### ImagePicker
```json
    {
     "key":"chooseImage",
     "type":"choose_image",
     "uploadButtonText":"Choose"
    }
```
key - must be unique in that particular step.

type - must be choose_image for ImagePicker.

uploadButtonText - text for Button of ImagePicker.

value - will be the path of chosen image on external storage

##### ImagePicker Required Validation

```json
"v_required" : {
                    "value" : "true",
                    "err" : "Please enter some value this is a required field."
               }
```

#### CheckBox (can be used for single/multiple CheckBoxes)
```json
    {
        "key":"checkData",
        "type":"check_box",
        "label":"Select multiple preferences",
        "options":[
            {
                "key":"awesomeness",
                "text":"Are you willing for some awesomeness?",
                "value":"false"
            },
            {
                "key":"newsletter",
                "text":"Do you really want to opt out from my newsletter?",
                "value":"false"
            }
        ]
    }

```

key - must be unique in that particular step.

type - must be check_box for CheckBox.

label - text for header of CheckBox.

options - options for CheckBox.

key(in options) - must be unique in options.

text(in options) - text fot the CheckBox.

value(in options) - true/false.

##### CheckBox Required Validation

Not supported yet.


#### Spinner
```json
        {
            "key":"name",
            "type":"spinner",
            "hint":"Name Thy House"
            "values":["Stark", "Targeriyan", "Lannister"]
        }
```

key - must be unique in that particular step.

type - must be spinner

hint - hint for Spinner.

values - Array of Strings.

value - will be the value present in the spinner after completion of wizard

##### Spinner Required Validation

```json
"v_required" : {
                    "value" : "true",
                    "err" : "Please enter some value this is a required field."
               }
```

#### RadioButton (can be used for single/multiple RadioButtons)

```json
{
    "key":"radioData",
    "type":"radio",
    "label":"Select one item from below",
    "options":[
        {
            "key":"areYouPro",
            "text":"Are you pro?"
        },
        {
            "key":"areYouAmature",
            "text":"Are you amature?"
        },
        {
            "key":"areYouNovice",
            "text":"Are you novice?"
        }
    ],
    "value":"areYouNovice"
}
```
key - must be unique in that particular step.

type - must be radio for RadioButton.

label - text for header of RadioButton.

value - must be key of one of the options which is selected/ or empty if no option is selected.

options - options for RadioButton.

key(in options) - must be unique in options.

text(in options) - text fot the RadioButton.

##### RadioButton Required Validation

Not supported yet.

## Demo Input Json (Complete)

```json
{
    "count":"3",
    "step1":{
        "fields":[
            {
                "key":"name",
                "type":"edit_text",
                "hint":"Enter Your Name",
                "v_min_length":{  "value" : "3",
                                    "err" : "Min length should be at least 3"
                                },
                "v_max_length":{  "value" : "10",
                    "err" : "Max length can be at most 10."
                }
            },
            {
                "key":"email",
                "type":"edit_text",
                "hint":"Enter Your Email",
                "v_email":{  "value" : "true",
                    "err" : "Not an email."
                }
            },
            {
                "key":"labelBackgroundImage",
                "type":"label",
                "text":"Choose Background Image"
            },
            {
                "key":"chooseImage",
                "type":"choose_image",
                "uploadButtonText":"Choose",
                "v_required":{  "value" : "true",
                    "err" : "Please choose an image to proceed."
                }
            },
            {
                "key":"house",
                "type":"spinner",
                "hint": "Name Thy House",
                "values":["Stark", "Targeriyan", "Lannister"],
                "v_required":{  "value" : "true",
                    "err" : "Please choose a value to proceed."
                }
            }
        ],
        "title":"Step 1 of 3",
        "next":"step2"
    },
    "step2":{
        "fields":[
            {
                "key":"name",
                "type":"edit_text",
                "hint":"Enter Country"
            },
            {
                "key":"checkData",
                "type":"check_box",
                "label":"Select multiple preferences",
                "options":[
                    {
                        "key":"awesomeness",
                        "text":"Are you willing for some awesomeness?",
                        "value":"false"
                    },
                    {
                        "key":"newsletter",
                        "text":"Do you really want to opt out from my newsletter?",
                        "value":"false"
                    }
                ]
            },
            {
                "key":"radioData",
                "type":"radio",
                "label":"Select one item from below",
                "options":[
                    {
                        "key":"areYouPro",
                        "text":"Are you pro?"
                    },
                    {
                        "key":"areYouAmature",
                        "text":"Are you amature?"
                    },
                    {
                        "key":"areYouNovice",
                        "text":"Are you novice?"
                    }
                ],
                "value":"areYouNovice"
            }
        ],
        "title":"Step 2 of 3",
        "next":"step3"
    },
    "step3":{
        "fields":[
            {
                "key":"anything",
                "type":"edit_text",
                "hint":"Enter Anything You Want"
            }
        ],
        "title":"Step 3 of 3"
    }
}
```

## Starting form activity with your json

```java
    Intent intent = new Intent(context, JsonFormActivity.class);
    String json = "Your complete JSON";
    intent.putExtra("json", json);
    startActivityForResult(intent, REQUEST_CODE_GET_JSON);
```

And receive result populated json in onActivityResult()

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_CODE_GET_JSON && resultCode == RESULT_OK) {
        Log.d(TAG, data.getStringExtra("json"));
    }
    super.onActivityResult(requestCode, resultCode, data);
}
```

## Output Json (of demo input json)

```json
{
    "count":"3",
    "step1":{
        "fields":[
            {
                "key":"name",
                "type":"edit_text",
                "hint":"Enter Your Name",
                "value":"Vijay"
            },
            {
                "key":"email",
                "type":"edit_text",
                "hint":"Enter Your Email",
                "value":"dummy@gmail.com"
            },
            {
                "key":"labelBackgroundImage",
                "type":"label",
                "text":"Choose Background Image"
            },
            {
                "key":"chooseImage",
                "type":"choose_image",
                "uploadButtonText":"Choose",
                "value":"\/storage\/emulated\/0\/Pictures\/Wally\/10017.png"
            }
        ],
        "title":"Step 1 of 3",
        "next":"step2"
    },
    "step2":{
        "fields":[
            {
                "key":"name",
                "type":"edit_text",
                "hint":"Enter Country",
                "value":"India"
            },
            {
                "key":"checkData",
                "type":"check_box",
                "label":"Select multiple preferences",
                "options":[
                    {
                        "key":"awesomeness",
                        "text":"Are you willing for some awesomeness?",
                        "value":"true"
                    },
                    {
                        "key":"newsletter",
                        "text":"Do you really want to opt out from my newsletter?",
                        "value":"false"
                    }
                ]
            },
            {
                "key":"radioData",
                "type":"radio",
                "label":"Select one item from below",
                "options":[
                    {
                        "key":"areYouPro",
                        "text":"Are you pro?"
                    },
                    {
                        "key":"areYouAmature",
                        "text":"Are you amature?"
                    },
                    {
                        "key":"areYouNovice",
                        "text":"Are you novice?"
                    }
                ],
                "value":"areYouPro"
            }
        ],
        "title":"Step 2 of 3",
        "next":"step3"
    },
    "step3":{
        "fields":[
            {
                "key":"anything",
                "type":"edit_text",
                "hint":"Enter Anything You Want",
                "value":"anything"
            }
        ],
        "title":"Step 3 of 3"
    }
}
```

# Including in your project

gradle:

Step 1. Add the JitPack repository to your build file

```groovy
repositories {
    maven {
        url "https://jitpack.io"
    }
}
```

Step 2. Add the dependency in the form

```groovy
dependencies {
    compile 'com.github.vijayrawatsan:android-json-form-wizard:1.0'
}
```

maven:

Step 1. Add the JitPack repository to your build file

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

Step 2. Add the dependency in the form

```xml
<dependency>
    <groupId>com.github.vijayrawatsan</groupId>
    <artifactId>android-json-form-wizard</artifactId>
    <version>1.0</version>
</dependency>
```
# TODOs

- Support validation for Checkbox and RadioButton.
- Improve image picker UI.

# Contributing
Contributions welcome via Github pull requests.

# Credits

- [material](https://github.com/rey5137/material)
- [MaterialEditText](https://github.com/rengwuxian/MaterialEditText)
- [MaterialSpinner](https://github.com/ganfra/MaterialSpinner)

Thanks!

# License
This project is licensed under the MIT License. Please refer the [License.txt](https://github.com/vijayrawatsan/android-json-form-wizard/blob/master/License.txt) file.
