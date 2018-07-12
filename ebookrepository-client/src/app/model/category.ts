export class Category {
    name: String;

    constructor(inter: CategoryInterface = {}) {
        this.name = inter.name;
    }
}
interface CategoryInterface {
    name?: String;
}