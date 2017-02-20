export class HttpStatus {
    private value: Number;
    private reasonPhrase: String;

    getValue() {
        return this.value;
    }

    getReasonPhrase() {
        return this.reasonPhrase;
    }
}