export class Language {
    id: number;
    name: String;

    constructor(inter: LanguageInterface = {}) {
        this.id = inter.id;
        this.name = inter.name;
    }
}
interface LanguageInterface {
    id?: number;
    name?: String;
}