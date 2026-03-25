const fs = require('fs');
let html = fs.readFileSync('index.html', 'utf8');

// 1. Move Promo Input from Subtotal UI to standalone const or just remove it from there
const oldPromoUI = `<div className="flex items-center space-x-2 mt-4 pt-4 border-t border-[#F0EBE1]">
                                                <input type="text" placeholder="Cupom de Desconto (PASCOA10)" value={promoInput} onChange={(e)=>setPromoInput(e.target.value)} className="flex-grow p-2 text-sm border border-[#F0EBE1] rounded-lg bg-beige-bg uppercase outline-none" />
                                                <button onClick={applyPromo} className="bg-choc-dark text-white text-xs px-4 py-2.5 rounded-lg font-bold hover:bg-[#38271B] transition-colors">Aplicar</button>
                                            </div>`;
html = html.replace(oldPromoUI, ''); // delete from subtotal

// 2. Add MaxLength to Name
html = html.replace(/<input type="text" value=\{formData\.name\}/g, '<input type="text" maxLength={30} value={formData.name}');

// 3. Add MaxLength to Phone and restrict to numbers
html = html.replace(/<input type="tel" value=\{formData\.phone\} onChange=\{e => setFormData\(\{ \.\.\.formData, phone: e\.target\.value \}\)\} className="w-full border border-\[#F0EBE1\] rounded-lg p-3 text-sm bg-beige-bg focus:ring-1 focus:ring-gold-soft outline-none" placeholder="\(00\) 00000-0000"/g, 
`<input type="tel" maxLength={11} value={formData.phone} onChange={e => setFormData({ ...formData, phone: e.target.value.replace(/\\D/g, '') })} className="w-full border border-[#F0EBE1] rounded-lg p-3 text-sm bg-beige-bg focus:ring-1 focus:ring-gold-soft outline-none" placeholder="Ex: 31991605336"`);

// 4. Insert Promo Input after the Name/Phone/Instagram block in FORMULÁRIO DE CHECKOUT
const targetAfterInstagram = `placeholder="@seu.perfil" /></div>
                                                </div>`;
const promoUIInForm = `placeholder="@seu.perfil" /></div>
                                                </div>

                                                <div className="pt-2">
                                                    <label className="text-xs font-bold text-choc-dark block mb-2">Cupom de Desconto</label>
                                                    <div className="flex items-center space-x-2">
                                                        <input type="text" maxLength={20} placeholder="PASCOA10" value={promoInput} onChange={(e)=>setPromoInput(e.target.value)} className="flex-grow p-3 text-sm border border-[#F0EBE1] rounded-lg bg-beige-bg uppercase outline-none focus:ring-1 focus:ring-gold-soft" />
                                                        <button type="button" onClick={applyPromo} className="bg-choc-dark text-white text-xs px-4 py-3 rounded-lg font-bold hover:bg-[#38271B] transition-colors">Validar</button>
                                                    </div>
                                                </div>`;
html = html.replace(targetAfterInstagram, promoUIInForm);

// 5. Add MaxLength to Obs
html = html.replace(/<textarea rows="2" value=\{formData\.obs\}/g, '<textarea rows="2" maxLength={500} value={formData.obs}');

fs.writeFileSync('index.html', html);
console.log('Form inputs updated successfully!');
