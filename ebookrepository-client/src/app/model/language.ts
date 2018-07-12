export class Language {
    name: String;

    constructor(inter: LanguageInterface = {}) {
        this.name = inter.name;
    }
}
interface LanguageInterface {
    name?: String;
}