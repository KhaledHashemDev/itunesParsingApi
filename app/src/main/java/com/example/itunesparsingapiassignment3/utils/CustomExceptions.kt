package com.example.itunesparsingapiassignment3.utils

//Class that takes a message and extends the exception class with that message
class NullResponseFromServer(message: String) : Exception(message)
class FailureResponseFromServer(message: String?) : Exception(message)