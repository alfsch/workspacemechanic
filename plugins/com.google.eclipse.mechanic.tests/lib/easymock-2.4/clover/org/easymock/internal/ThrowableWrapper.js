var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = { "classes" : [
    {"id" : 4145, "sl" : 7, "el" : 20, "name" : "ThrowableWrapper",
    "methods" : [
              {"sl" : 13, "el" : 15, "sc" : 5},  {"sl" : 17, "el" : 19, "sc" : 5}  ]}
    
 ]
};

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {
		"test_247" : {
					  "name" : "object",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_252" : {
					  "name" : "longAndRange",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_194" : {
					  "name" : "booleanType",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_226" : {
					  "name" : "longAndCount",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_249" : {
					  "name" : "doubleType",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_299" : {
					  "name" : "throwableAndDefaultThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_197" : {
					  "name" : "throwRuntimeException",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_68" : {
					  "name" : "objectAndMinMax",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_186" : {
					  "name" : "openCallCountByLastThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_285" : {
					  "name" : "doubleType",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_264" : {
					  "name" : "objectAndRange",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_268" : {
					  "name" : "defaultVoidCallable",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_283" : {
					  "name" : "object",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_82" : {
					  "name" : "doubleAndMinMax",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_106" : {
					  "name" : "exactCallCountByLastThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_278" : {
					  "name" : "defaultThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_236" : {
					  "name" : "twoThrows",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_244" : {
					  "name" : "booleanAndMinMax",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_261" : {
					  "name" : "booleanAndCount",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_189" : {
					  "name" : "throwCheckedException",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_190" : {
					  "name" : "exactCallCountByLastThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_200" : {
					  "name" : "throwError",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_206" : {
					  "name" : "answer",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_201" : {
					  "name" : "longType",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_172" : {
					  "name" : "openCallCountByLastThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_199" : {
					  "name" : "throwSubclassOfError",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_294" : {
					  "name" : "floatType",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_203" : {
					  "name" : "floatType",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_449" : {
					  "name" : "moreThanOneResultAndOpenCallCount",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_275" : {
					  "name" : "stubWithReturnValue",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_195" : {
					  "name" : "throwSubclassOfRuntimeException",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_328" : {
					  "name" : "booleanType",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_44" : {
					  "name" : "floatAndMinMax",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_191" : {
					  "name" : "throwSubclassOfCheckedException",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_255" : {
					  "name" : "doubleAndRange",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_258" : {
					  "name" : "floatAndRange",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_230" : {
					  "name" : "floatAndCount",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_229" : {
					  "name" : "doubleAndCount",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_232" : {
					  "name" : "objectAndCount",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_325" : {
					  "name" : "longType",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_333" : {
					  "name" : "throwAfterReturnValue",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_403" : {
					  "name" : "defaultThrowable",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_57" : {
					  "name" : "longAndMinMax",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  },
		"test_254" : {
					  "name" : "booleanAndRange",
					  "pass" : true ,
					  "methods" : [{"sl": 13 },{"sl": 17 },],
					  "statements" : [{"sl": 14 },{"sl": 18 },]
					  }
 };

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [  [],   [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [  ] ,
  [ 206 , 264 , 255 , 249 , 199 , 226 , 285 , 236 , 195 , 403 , 275 , 203 , 200 , 254 , 201 , 229 , 44 , 252 , 325 , 230 , 191 , 82 , 261 , 186 , 232 , 244 , 197 , 172 , 194 , 68 , 278 , 106 , 258 , 247 , 190 , 333 , 294 , 57 , 299 , 449 , 283 , 268 , 189 , 328   ] ,
  [ 206 , 264 , 255 , 249 , 199 , 226 , 285 , 236 , 195 , 403 , 275 , 203 , 200 , 254 , 201 , 229 , 44 , 252 , 325 , 230 , 191 , 82 , 261 , 186 , 232 , 244 , 197 , 172 , 194 , 68 , 278 , 106 , 258 , 247 , 190 , 333 , 294 , 57 , 299 , 449 , 283 , 268 , 189 , 328   ] ,
  [  ] ,
  [  ] ,
  [ 206 , 264 , 255 , 249 , 199 , 226 , 285 , 236 , 195 , 403 , 275 , 203 , 200 , 254 , 201 , 229 , 44 , 252 , 325 , 230 , 191 , 82 , 261 , 186 , 232 , 244 , 197 , 172 , 194 , 68 , 278 , 106 , 258 , 247 , 190 , 333 , 294 , 57 , 299 , 449 , 283 , 268 , 189 , 328   ] ,
  [ 206 , 264 , 255 , 249 , 199 , 226 , 285 , 236 , 195 , 403 , 275 , 203 , 200 , 254 , 201 , 229 , 44 , 252 , 325 , 230 , 191 , 82 , 261 , 186 , 232 , 244 , 197 , 172 , 194 , 68 , 278 , 106 , 258 , 247 , 190 , 333 , 294 , 57 , 299 , 449 , 283 , 268 , 189 , 328   ] ,
  [  ] ,
  [  ] 
];
