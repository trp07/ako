(function (modules) { // webpackBootstrap
    // The module cache

    var installedModules = {};

    // The require function

    function __webpack_require__(moduleId) {

        // Check if module is in cache

        if (installedModules[moduleId])

            return installedModules[moduleId].exports;

        // Create a new module (and put it into the cache)

        var module = installedModules[moduleId] = {

            exports: {},

            id: moduleId,

            loaded: false

        };

        // Execute the module function

        modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);

        // Flag the module as loaded

        module.loaded = true;

        // Return the exports of the module

        return module.exports;

    }


    // expose the modules object (__webpack_modules__)

    __webpack_require__.m = modules;

    // expose the module cache

    __webpack_require__.c = installedModules;

    // __webpack_public_path__

    __webpack_require__.p = "";

    // Load entry module and return exports

    return __webpack_require__(0);

})
/************************************************************************/
([

    function (module, exports, __webpack_require__) {

        'use strict';

        var _mdAccordion = __webpack_require__(1);

        var _mdAccordion2 = _interopRequireDefault(_mdAccordion);

        var _mdAccordionGroup = __webpack_require__(4);

        var _mdAccordionGroup2 = _interopRequireDefault(_mdAccordionGroup);

        var _mdAccordionContent = __webpack_require__(6);

        var _mdAccordionContent2 = _interopRequireDefault(_mdAccordionContent);

        var _mdAccordionButton = __webpack_require__(9);

        var _mdAccordionButton2 = _interopRequireDefault(_mdAccordionButton);

        function _interopRequireDefault(obj) {
            return obj && obj.__esModule ? obj : {
                default: obj
            };
        }

        (function (angular) {

            angular.module('ngMaterialAccordion', ['ngMaterial']).directive(_mdAccordion2.default.name, _mdAccordion2.default.directive).directive(_mdAccordionGroup2.default.name, _mdAccordionGroup2.default.directive).directive(_mdAccordionContent2.default.name, _mdAccordionContent2.default.directive).directive(_mdAccordionButton2.default.name, _mdAccordionButton2.default.directive);
        })(angular);


    },

    function (module, exports, __webpack_require__) {

        'use strict';

        Object.defineProperty(exports, "__esModule", {
            value: true
        });

        var _template = __webpack_require__(2);

        var _template2 = _interopRequireDefault(_template);

        var _link = __webpack_require__(3);

        var _link2 = _interopRequireDefault(_link);

        function _interopRequireDefault(obj) {
            return obj && obj.__esModule ? obj : {
                default: obj
            };
        }

        var directive = function directive() {

            return {
                restrict: 'E',
                scope: {
                    locked: '@?mdLocked'
                },
                replace: true,
                transclude: true,
                template: _template2.default,
                link: _link2.default
            };
        };

        exports.default = {
            name: 'mdAccordion',
            directive: directive
        };


    },

    function (module, exports) {

        'use strict';

        Object.defineProperty(exports, "__esModule", {
            value: true
        });

        exports.default = function ($element, $attributes) {

            var locked = $attributes.locked && 'md-accordion-locked';

            return '<div class="md-accordion ' + locked + '" ng-transclude></div>';
        };


    },

    function (module, exports) {

        'use strict';

        Object.defineProperty(exports, "__esModule", {
            value: true
        });

        exports.default = function ($scope, $element, $attributes) {

            $scope.$watch(function () {
                return $attributes.locked;
            }, function (locked) {
                if (locked) {
                    $element[0].classList.add('md-accordion-locked');
                } else {
                    $element[0].classList.remove('md-accordion-locked');
                }
            });
        };


    },

    function (module, exports, __webpack_require__) {

        'use strict';

        Object.defineProperty(exports, "__esModule", {
            value: true
        });

        var _template = __webpack_require__(5);

        var _template2 = _interopRequireDefault(_template);

        function _interopRequireDefault(obj) {
            return obj && obj.__esModule ? obj : {
                default: obj
            };
        }

        var directive = function directive() {

            return {
                restrict: 'E',
                replace: true,
                transclude: true,
                template: _template2.default
            };
        };

        exports.default = {
            name: 'mdAccordionGroup',
            directive: directive
        };


    },

    function (module, exports) {

        'use strict';

        Object.defineProperty(exports, "__esModule", {
            value: true
        });

        exports.default = function () {

            return '<div class="md-accordion-group" flex layout="column" layout-align="start start" ng-transclude></div>';
        };


    },

    function (module, exports, __webpack_require__) {

        'use strict';

        Object.defineProperty(exports, "__esModule", {
            value: true
        });

        var _controller = __webpack_require__(7);

        var _controller2 = _interopRequireDefault(_controller);

        var _template = __webpack_require__(8);

        var _template2 = _interopRequireDefault(_template);

        function _interopRequireDefault(obj) {
            return obj && obj.__esModule ? obj : {
                default: obj
            };
        }

        var directive = function directive() {

            return {
                restrict: 'E',
                scope: {
                    heading: '@mdHeading',
                    icon: '@?mdIcon',
                    svgIcon: '@?mdSvgIcon',
                    arrow: '@?mdArrow'
                },
                replace: true,
                transclude: true,
                template: _template2.default,
                controller: _controller2.default,
                controllerAs: '$mdAccordionContent',
                bindToController: true
            };
        };

        exports.default = {
            name: 'mdAccordionContent',
            directive: directive
        };


    },

    function (module, exports) {

        "use strict";

        Object.defineProperty(exports, "__esModule", {
            value: true
        });

        exports.default = function () {

            this.visible = false;

            this.changeState = function () {
                this.visible = !this.visible;
            };
        };


    },

    function (module, exports) {

        "use strict";

        Object.defineProperty(exports, "__esModule", {
            value: true
        });

        exports.default = function () {

            return "\n    <div class=\"md-accordion-content\" layout=\"column\">\n      <md-button class=\"md-accordion-toggle\" ng-if=\"$mdAccordionContent.heading\" ng-click=\"$mdAccordionContent.changeState();\" ng-class=\"{ 'md-active': $mdAccordionContent.visible }\">\n        <div layout=\"row\">\n          <md-icon ng-if=\"$mdAccordionContent.svgIcon\" md-svg-icon=\"$mdAccordionContent.svgIcon\"></md-icon>\n          <md-icon ng-if=\"$mdAccordionContent.icon\">{{ $mdAccordionContent.icon }}</md-icon>\n          <span flex>{{ $mdAccordionContent.heading }}</span>\n          <i ng-if=\"$mdAccordionContent.arrow\" class=\"fa fa-chevron-down accordion-arrow\" aria-hidden=\"true\"></i>\n        </div>\n      </md-button>\n\n      <div class=\"md-accordion-wrapper\" md-accordion-disable-animate ng-class=\"{ 'md-active': $mdAccordionContent.visible, 'md-accordion-wrapper-icons':  $mdAccordionContent.icon }\" layout=\"column\" ng-transclude></div>\n    </div>\n  ";
        };


    },

    function (module, exports, __webpack_require__) {

        'use strict';

        Object.defineProperty(exports, "__esModule", {
            value: true
        });

        var _controller = __webpack_require__(10);

        var _controller2 = _interopRequireDefault(_controller);

        var _template = __webpack_require__(11);

        var _template2 = _interopRequireDefault(_template);

        function _interopRequireDefault(obj) {
            return obj && obj.__esModule ? obj : {
                default: obj
            };
        }

        var directive = function directive() {

            return {
                restrict: 'E',
                scope: {
                    ngClick: '@?',
                    uiSref: '@?',
                    uiSrefActive: '@?',
                    href: '@?',
                    target: '@?'
                },
                transclude: true,
                template: _template2.default,
                controller: _controller2.default,
                controllerAs: '$mdAccordionButton',
                bindToController: true
            };
        };

        exports.default = {
            name: 'mdAccordionButton',
            directive: directive
        };


    },

    function (module, exports) {

        "use strict";

        Object.defineProperty(exports, "__esModule", {
            value: true
        });

        exports.default = function () {};


    },

    function (module, exports) {

        "use strict";

        Object.defineProperty(exports, "__esModule", {
            value: true
        });

        exports.default = function () {

            return "\n    <md-button\n      class=\"md-accordion-button\"\n      layout=\"column\"\n      ng-attr-href=\"{{ $mdAccordionButton.href }}\"\n      ng-attr-target=\"{{ $mdAccordionButton.target }}\">\n      <div layout=\"row\" layout-fill layout-align=\"start center\" ng-transclude></div>\n    </md-button>\n  ";
        };


    }

    ]);
