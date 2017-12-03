akoApp.component('sidenavComponent', {
    templateUrl: "/app/component/sidenav/sidenavTemplate.html",
    bindings: {},
    controller: function () {

        var sections = [{
            name: 'Getting Started',
            url: 'getting-started',
            type: 'link'
        }];

        this.selectSection = function (section) {
            this.openedSection = section;
        };
        this.toggleSelectSection = function (section) {
            this.openedSection = (this.openedSection === section ? null : section);
        };
        this.isSectionSelected = function (section) {
            return this.openedSection === section;
        };

        this.selectPage = function (section, page) {
            this.currentSection = section;
            this.currentPage = page;
        };
        this.isPageSelected = function (page) {
            return this.currentPage === page;
        };
        this.focusMainContent = function () {}
        sections.push();

        sections.push({
            name: 'Customization',
            type: 'heading',
            children: [
                {
                    name: 'CSS',
                    type: 'toggle',
                    pages: [{
                            name: 'Typography',
                            url: 'CSS/typography',
                            type: 'link'
                            },
                        {
                            name: 'Button',
                            url: 'CSS/button',
                            type: 'link'
                            },
                        {
                            name: 'Checkbox',
                            url: 'CSS/checkbox',
                            type: 'link'
                            }]
                },
                {
                    name: 'Theming',
                    type: 'toggle',
                    pages: [
                        {
                            name: 'Introduction and Terms',
                            url: 'Theming/01_introduction',
                            type: 'link'
                        },
                        {
                            name: 'Declarative Syntax',
                            url: 'Theming/02_declarative_syntax',
                            type: 'link'
                        },
                        {
                            name: 'Configuring a Theme',
                            url: 'Theming/03_configuring_a_theme',
                            type: 'link'
                        },
                        {
                            name: 'Multiple Themes',
                            url: 'Theming/04_multiple_themes',
                            type: 'link'
                        },
                        {
                            name: 'Under the Hood',
                            url: 'Theming/05_under_the_hood',
                            type: 'link'
                        },
                        {
                            name: 'Browser Color',
                            url: 'Theming/06_browser_color',
                            type: 'link'
                        }
                        ]
                }
                ]
        });
        this.sections = sections;
    }
});
